package eu.crushedpixel.sponge.masquerade.masquerades;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Preconditions;
import eu.crushedpixel.sponge.masquerade.utils.ReflectionUtils;
import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListenerAdapter;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static eu.crushedpixel.sponge.masquerade.utils.PacketMath.rotationToByte;
import static eu.crushedpixel.sponge.masquerade.utils.PacketMath.velocityToShort;

public abstract class Masquerade<E extends Entity> extends PacketListenerAdapter {

    @Getter
    private final UUID playerUUID;

    private final PacketGate packetGate;

    private final Set<PacketConnection> deceived = new HashSet<>();

    // the player's entity ID
    protected int entityID;

    // the fake entity uuid
    protected final UUID entityUUID = UUID.randomUUID();

    private final Class<? extends E> entityClass;

    private final Set<DataParameter<?>> validDataKeys;
    private final Set<IAttribute> validAttributes;

    public Masquerade(Player player, Class<? extends E> entityClass) {
        this.playerUUID = player.getUniqueId();
        this.entityID = ((EntityPlayer) player).getEntityId();
        this.entityClass = entityClass;
        this.packetGate = Sponge.getServiceManager().provide(PacketGate.class).get();
        this.validDataKeys = findDataKeys(entityClass);
        this.validAttributes = registerAttributes(entityClass);
    }

    /**
     * Shows this Masquerade to the specified player.
     * @param player
     */
    public void maskTo(Player player) {
        Preconditions.checkArgument(!player.getUniqueId().equals(this.playerUUID),
                "Can't show the Masquerade to the masked player");
        packetGate.connectionByPlayer(player).ifPresent(this::maskTo);
    }

    /**
     * Unmasks the player for the specified player.
     * @param player
     */
    public void unmaskTo(Player player) {
        packetGate.connectionByPlayer(player).ifPresent(this::unmaskTo);
    }

    /**
     * Unmasks the player to all deceived players
     */
    public void unmask() {
        deceived.forEach(this::unmaskTo);
    }

    private void maskTo(PacketConnection connection) {
        Optional<Player> optional = Sponge.getServer().getPlayer(playerUUID);
        if (!optional.isPresent()) throw new RuntimeException("Could not apply Masquerade: Masked player is not available");
        Player player = optional.get();

        // first, send a packet telling the client that the player entity has been removed
        despawnEntity(connection);

        // then, spawn the fake entity with the same ID that the player entity used to have
        // so that all further player packets refer to the masquerade instead
        spawnEntity(player, connection);

        if (deceived.isEmpty()) {
            registerListeners();
        }

        deceived.add(connection);
    }

    private void unmaskTo(PacketConnection connection) {
        deceived.remove(connection);

        // despawn the fake entity
        despawnEntity(connection);

        // spawn the player again if available
        Sponge.getServer().getPlayer(playerUUID).ifPresent(player -> spawnPlayer(player, connection));

        if (deceived.isEmpty()) {
            unregisterListeners();
        }
    }

    private Set<DataParameter<?>> findDataKeys(Class<? extends Entity> entityClass) {
        Set<DataParameter<?>> dataKeys = new HashSet<>();
        for (Field field : ReflectionUtils.getAllFields(entityClass)) {
            if (field.getType() != DataParameter.class) continue;
            try {
                field.setAccessible(true);
                DataParameter<?> parameter = (DataParameter<?>) field.get(null);
                dataKeys.add(parameter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataKeys;
    }

    // override this to allow for more attributes for specific masquerades
    protected Set<IAttribute> registerAttributes(Class<? extends Entity> entityClass) {
        if (!EntityLivingBase.class.isAssignableFrom(entityClass)) {
            return Collections.emptySet();
        }

        Set<IAttribute> attributes = new HashSet<>();

        // these attributes are registered for all entity classes extending EntityLivingBase,
        // so they are safe to transmit
        attributes.add(SharedMonsterAttributes.MAX_HEALTH);
        attributes.add(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        attributes.add(SharedMonsterAttributes.MOVEMENT_SPEED);
        attributes.add(SharedMonsterAttributes.ARMOR);
        attributes.add(SharedMonsterAttributes.ARMOR_TOUGHNESS);

        return attributes;
    }

    private void registerListeners() {
        // register this packet listener to listen to all packets to allow subclasses
        // to override the onPacketWrite and onPacketRead methods
        packetGate.registerListener(this, ListenerPriority.LAST);
    }

    private void unregisterListeners() {
        packetGate.unregisterListener(this);
    }

    private List<Packet> createSpawnPackets(Location location, Vector3d rotation, Vector3d headRotation, Vector3d velocity) {
        return createSpawnPackets(
                location.getX(), location.getY(), location.getZ(),
                rotationToByte(rotation.getX()), rotationToByte(rotation.getY()),
                rotationToByte(headRotation.getY()),
                velocityToShort(velocity.getX()), velocityToShort(velocity.getY()), velocityToShort(velocity.getZ())
        );
    }

    abstract List<Packet> createSpawnPackets(double posX, double posY, double posZ, byte yaw, byte pitch, byte headPitch, short velX, short velY, short velZ);

    protected void despawnEntity(PacketConnection connection) {
        connection.sendPacket(new SPacketDestroyEntities(entityID));
    }

    protected void spawnPlayer(Player player, PacketConnection connection) {
        connection.sendPacket(new SPacketSpawnPlayer((EntityPlayer) player));
    }

    protected void spawnEntity(Player player, PacketConnection connection) {
        List<Packet> packets = createSpawnPackets(
                player.getLocation(),
                player.getRotation(),
                player.getHeadRotation(),
                player.getVelocity());

        sendPackets(packets, connection);
    }

    protected void sendPackets(List<Packet> packets, PacketConnection connection) {
        packets.forEach(packet -> connection.sendPacket(packet));
    }

    @Override
    public void onPacketWrite(PacketEvent packetEvent, PacketConnection connection) {
        if (!deceived.contains(connection)) return;

        Packet packet = packetEvent.getPacket();

        // replace spawn player packets with the disguise spawn
        if (packet instanceof SPacketSpawnPlayer) {
            SPacketSpawnPlayer packetSpawnPlayer = (SPacketSpawnPlayer) packet;

            // when a new SPacketSpawnPlayer is sent for the masked player,
            // update the entityID as it has most likely changed
            if (packetSpawnPlayer.uniqueId.equals(this.entityUUID)) {
                this.entityID = packetSpawnPlayer.entityId;
            }

            if (packetSpawnPlayer.entityId != this.entityID) return;

            packetEvent.setCancelled(true);

            sendPackets(createSpawnPackets(
                    packetSpawnPlayer.x, packetSpawnPlayer.y, packetSpawnPlayer.z,
                    packetSpawnPlayer.yaw, packetSpawnPlayer.pitch, rotationToByte(0),
                    (short) 0, (short) 0, (short) 0
            ), connection);
        }

        if (packet instanceof SPacketEntityMetadata) {
            SPacketEntityMetadata packetEntityMetadata = (SPacketEntityMetadata) packet;
            if (packetEntityMetadata.entityId != this.entityID) return;

            // use reflection to check if the data keys are valid for the fake entity's type
            Iterator<DataEntry<?>> it = packetEntityMetadata.dataManagerEntries.iterator();
            while (it.hasNext()) {
                DataEntry dataEntry = it.next();

                boolean valid = false;
                for (DataParameter<?> key : validDataKeys) {
                    if (key == dataEntry.getKey()) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    it.remove();
                }
            }

            if (packetEntityMetadata.dataManagerEntries.isEmpty()) {
                packetEvent.setCancelled(true);
            }
        }

        if (packet instanceof SPacketEntityProperties) {
            SPacketEntityProperties packetEntityProperties = (SPacketEntityProperties) packet;
            if (packetEntityProperties.entityId != this.entityID) return;

            // property packets are only accepted by the client for living entities
            if (!EntityLivingBase.class.isAssignableFrom(entityClass)) {
                packetEvent.setCancelled(true);
            }

            // player entities have attributes that other entity types may not have, for example luck.
            // remove all attributes that are not registered for the masquerade's entity type.

            Iterator<SPacketEntityProperties.Snapshot> it = packetEntityProperties.snapshots.iterator();

            while (it.hasNext()) {
                SPacketEntityProperties.Snapshot snapshot = it.next();

                boolean valid = false;
                for (IAttribute attribute : validAttributes) {
                    if (snapshot.getName().equals(attribute.getName())) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    it.remove();
                }
            }

            if (packetEntityProperties.snapshots.isEmpty()) {
                packetEvent.setCancelled(true);
            }
        }
    }
}
