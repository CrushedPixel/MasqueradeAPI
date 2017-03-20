package eu.crushedpixel.sponge.masquerade.api.masquerades;

import eu.crushedpixel.sponge.masquerade.api.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.InvertedBooleanMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.flag.BooleanFlagEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.utils.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketSpawnMob;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MobMasquerade<E extends Entity> extends AbstractMasquerade<E> {

    private static final int FLAG_ON_FIRE = 0;
    private static final int FLAG_SNEAKING = 1;
    private static final int FLAG_SPRINTING = 3;
    private static final int FLAG_INVISIBLE = 5;
    private static final int FLAG_GLOWING = 6;

    private DataEntry<Byte> flags;

    // the entity type
    protected final int typeID;

    public MobMasquerade(Player player, EntityType entityType) {
        super(player, (Class<? extends E>) entityType.getEntityClass());

        Integer typeID = EntityList.CLASS_TO_ID.get(entityType.getEntityClass());
        if (typeID == null) throw new IllegalArgumentException("Provided entity class could not be mapped to entity ID");

        this.typeID = typeID;
    }

    @Override
    protected void registerKeys() {
        flags = new DataEntry<>(Entity.FLAGS, (byte) 0);

        registerKey(Keys.IS_AFLAME, new BooleanFlagEntityMetadata(this, flags, FLAG_ON_FIRE));
        registerKey(Keys.IS_SNEAKING, new BooleanFlagEntityMetadata(this, flags, FLAG_SNEAKING));
        registerKey(Keys.IS_SPRINTING, new BooleanFlagEntityMetadata(this, flags, FLAG_SPRINTING));
        registerKey(Keys.INVISIBLE, new BooleanFlagEntityMetadata(this, flags, FLAG_INVISIBLE));
        registerKey(Keys.GLOWING, new BooleanFlagEntityMetadata(this, flags, FLAG_GLOWING));

        registerKey(Keys.REMAINING_AIR, new BasicEntityMetadata<>(this, Entity.AIR, 300));
        registerKey(Keys.DISPLAY_NAME, new EntityMetadata<String, Text>(this, Entity.CUSTOM_NAME, "") {

            @Override
            protected Text convertToExternal(String value) {
                return Text.of(value);
            }

            @Override
            protected String convertToInternal(Text value) {
                return value.toPlain();
            }
        });

        registerKey(Keys.CUSTOM_NAME_VISIBLE, new BasicEntityMetadata<>(this, Entity.CUSTOM_NAME_VISIBLE, false));
        registerKey(Keys.IS_SILENT, new BasicEntityMetadata<>(this, Entity.SILENT, false));
        registerKey(Keys.HAS_GRAVITY, new InvertedBooleanMetadata(this, Entity.NO_GRAVITY, false));
    }

    @Override
    public List<Packet> createSpawnPackets(double posX, double posY, double posZ,
                                           byte yaw, byte pitch, byte headYaw,
                                           short velX, short velY, short velZ) {
        List<Packet> packetList = new ArrayList<>();

        SPacketSpawnMob packetSpawnMob = PacketUtils.createPacketSpawnMob(
                this.entityID, this.entityUUID, this.typeID,
                posX, posY, posZ, yaw, pitch, headYaw, velX, velY, velZ);

        packetList.add(packetSpawnMob);
        return packetList;
    }

}
