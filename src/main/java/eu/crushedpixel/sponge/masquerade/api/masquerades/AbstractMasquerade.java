package eu.crushedpixel.sponge.masquerade.api.masquerades;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Preconditions;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static eu.crushedpixel.sponge.masquerade.api.utils.PacketUtils.rotationToByte;
import static eu.crushedpixel.sponge.masquerade.api.utils.PacketUtils.velocityToShort;

public abstract class AbstractMasquerade<E extends Entity> implements Masquerade {

    private final UUID playerUUID;

    private final PacketGate packetGate;

    /**
     * Connections that are deceived by this AbstractMasquerade.
     */
    private final Map<UUID, MasqueradePacketConnection> deceived = new ConcurrentHashMap<>();

    // the player's entity ID
    protected int entityID;

    // the fake entity uuid
    protected final UUID entityUUID = UUID.randomUUID();

    private final Class<? extends E> entityClass;

    private final Set<IAttribute> validAttributes;

    private final Map<Key<?>, EntityMetadata<?, ?>> metadata = new HashMap<>();

    public AbstractMasquerade(Player player, Class<? extends E> entityClass) {
        this.playerUUID = player.getUniqueId();
        this.entityID = ((EntityPlayer) player).getEntityId();
        this.entityClass = entityClass;
        this.packetGate = Sponge.getServiceManager().provide(PacketGate.class).get();
        this.validAttributes = registerAttributes(entityClass);
        registerKeys();
    }

    @Override
    public void maskTo(Player player) {
        Preconditions.checkArgument(!player.getUniqueId().equals(this.playerUUID),
                "Can't show the AbstractMasquerade to the masked player");

        // wrap the connection in a MasqueradePacketConnection
        MasqueradePacketConnection connection = new MasqueradePacketConnection(
                this, packetGate.connectionByPlayer(player).get());

        Player masked = Sponge.getServer().getPlayer(playerUUID).get();

        // only spawn the entity if the players are in the same world
        if (player.getWorld().getUniqueId().equals(masked.getWorld().getUniqueId())) {
            // first, send a packet telling the client that the player entity has been removed
            despawnEntity(connection);

            // then, spawn the fake entity with the same ID that the player entity used to have
            // so that all further player packets refer to the masquerade instead
            spawnEntity(masked, connection);

            // send all entity metadata
            sendEntityData(connection);
        }

        connection.register();
        deceived.put(player.getUniqueId(), connection);
    }

    protected abstract void registerKeys();

    protected <V> void registerKey(Key<? extends BaseValue<V>> key, EntityMetadata<?, V> entityMetadata) {
        metadata.put(key, entityMetadata);
    }

    @Override
    public void unmaskTo(Player player) {
        UUID uuid = player.getUniqueId();
        MasqueradePacketConnection connection = deceived.get(uuid);

        if (connection != null) {
            unmaskTo(uuid, connection);
        }
    }

    @Override
    public void unmask() {
        deceived.forEach(this::unmaskTo);
    }

    private void unmaskTo(UUID uuid, MasqueradePacketConnection connection) {
        connection.unregister();
        deceived.remove(uuid);

        // despawn the fake entity
        despawnEntity(connection);

        // spawn the player again if available
        Sponge.getServer().getPlayer(this.playerUUID).ifPresent(player -> spawnPlayer(player, connection));
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

    private List<Packet> createSpawnPackets(Location location, Vector3d rotation, Vector3d headRotation, Vector3d velocity) {
        return createSpawnPackets(
                location.getX(), location.getY(), location.getZ(),
                rotationToByte(rotation.getY()), rotationToByte(rotation.getX()),
                rotationToByte(headRotation.getY()),
                velocityToShort(velocity.getX()), velocityToShort(velocity.getY()), velocityToShort(velocity.getZ())
        );
    }

    abstract List<Packet> createSpawnPackets(double posX, double posY, double posZ, byte yaw, byte pitch, byte headPitch, short velX, short velY, short velZ);

    void despawnEntity(MasqueradePacketConnection connection) {
        connection.sendPacket(new SPacketDestroyEntities(entityID));
    }

    private void spawnPlayer(Player player, MasqueradePacketConnection connection) {
        connection.sendPacket(new SPacketSpawnPlayer((EntityPlayer) player));
    }

    private void spawnEntity(Player player, MasqueradePacketConnection connection) {
        List<Packet> packets = createSpawnPackets(
                player.getLocation(),
                player.getRotation(),
                player.getHeadRotation(),
                player.getVelocity());

        connection.sendPackets(packets);
    }

    void sendEntityData(MasqueradePacketConnection connection) {
        SPacketEntityMetadata packetEntityMetadata = new SPacketEntityMetadata();
        packetEntityMetadata.entityId = this.entityID;
        packetEntityMetadata.dataManagerEntries = new ArrayList<>();

        for (EntityMetadata<?, ?> entityMetadata : metadata.values()) {
            // don't send duplicate data entries over
            if (!packetEntityMetadata.dataManagerEntries.contains(entityMetadata.getDataEntry())) {
                packetEntityMetadata.dataManagerEntries.add(entityMetadata.getDataEntry());
            }
        }

        connection.sendPacket(packetEntityMetadata);
    }

    void handlePacketEntityMetadata(SPacketEntityMetadata packet) {
        // check if the manipulators keys are valid for the fake entity's type
        Collection<EntityMetadata<?, ?>> metadataEntries = metadata.values();

        ListIterator<DataEntry<?>> it = packet.dataManagerEntries.listIterator();
        while (it.hasNext()) {
            DataEntry dataEntry = it.next();

            boolean wasApplied = false;

            for (EntityMetadata<?, ?> metadata : metadataEntries) {
                DataEntry<?> handledEntry = metadata.handleOutgoingDataEntry(dataEntry);

                if (handledEntry != null) {
                    dataEntry = handledEntry;
                    it.set(handledEntry);
                    wasApplied = true;
                }
            }

            // if the outgoing data entry wasn't applied to the AbstractMasquerade's data model,
            // remove it from the packet
            if (!wasApplied) {
                it.remove();
            }
        }
    }

    public void sendToAll(Packet packet) {
        deceived.forEach((uuid, connection) -> connection.sendPacket(packet));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> void setData(Key<? extends BaseValue<V>> key, V value) {
        Preconditions.checkArgument(hasKey(key), "Unknown key for this masquerade type");
        EntityMetadata<?, V> entityMetadata = (EntityMetadata<?, V>) metadata.get(key);
        entityMetadata.setValue(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V getData(Key<? extends BaseValue<V>> key) {
        Preconditions.checkArgument(hasKey(key), "Unknown key for this masquerade type");
        EntityMetadata<?, V> entityMetadata = (EntityMetadata<?, V>) metadata.get(key);
        return entityMetadata.getValue();
    }

    @Override
    public <V> boolean hasKey(Key<? extends BaseValue<V>> key) {
        return metadata.containsKey(key);
    }

    @Override
    public Collection<Key<?>> getKeys() {
        return metadata.keySet();
    }

    @Override
    public void setAllowValueChange(Key key, boolean allowValueChange) {
        Preconditions.checkArgument(hasKey(key), "Unknown key for this masquerade type");
        EntityMetadata<?, ?> entityMetadata = metadata.get(key);
        entityMetadata.setAllowValueChange(allowValueChange);
    }

    @Override
    public boolean allowsValueChange(Key key) {
        Preconditions.checkArgument(hasKey(key), "Unknown key for this masquerade type");
        EntityMetadata<?, ?> entityMetadata = metadata.get(key);
        return entityMetadata.isAllowValueChange();
    }

    UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getEntityID() {
        return entityID;
    }

    void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    Class<? extends E> getEntityClass() {
        return entityClass;
    }

    Set<IAttribute> getValidAttributes() {
        return validAttributes;
    }

}
