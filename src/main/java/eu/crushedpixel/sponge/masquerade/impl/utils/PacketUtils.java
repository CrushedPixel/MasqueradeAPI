package eu.crushedpixel.sponge.masquerade.impl.utils;

import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketUtils {

    public static byte rotationToByte(double rotation) {
        return (byte) (rotation * 256f / 360f);
    }

    public static short velocityToShort(double velocity) {
        return (short) (Math.min(3.9, Math.max(-3.9, velocity)) * 8000);
    }

    public static SPacketEntityMetadata clonePacketEntityMetadata(SPacketEntityMetadata packetIn) {
        SPacketEntityMetadata packetEntityMetadata = new SPacketEntityMetadata();
        packetEntityMetadata.entityId = packetIn.entityId;
        packetEntityMetadata.dataManagerEntries = new ArrayList<>();

        for (DataEntry<?> dataEntry : packetIn.dataManagerEntries) {
            DataEntry cloned = new DataEntry(dataEntry.getKey(), dataEntry.getValue());
            cloned.setDirty(dataEntry.isDirty());
            packetEntityMetadata.dataManagerEntries.add(cloned);
        }

        return packetEntityMetadata;
    }

    public static SPacketSpawnMob createPacketSpawnMob(int entityID, UUID entityUUID, int entityType,
                                                       double posX, double posY, double posZ,
                                                       byte yaw, byte pitch, byte headYaw,
                                                       short velX, short velY, short velZ) {
        SPacketSpawnMob packetSpawnMob = new SPacketSpawnMob();
        packetSpawnMob.entityId = entityID;
        packetSpawnMob.uniqueId = entityUUID;
        packetSpawnMob.type = entityType;
        packetSpawnMob.x = posX;
        packetSpawnMob.y = posY;
        packetSpawnMob.z = posZ;
        packetSpawnMob.yaw = yaw;
        packetSpawnMob.pitch = pitch;
        packetSpawnMob.headPitch = headYaw; // the packet's field is incorrectly translated
        packetSpawnMob.velocityX = velX;
        packetSpawnMob.velocityY = velY;
        packetSpawnMob.velocityZ = velZ;
        packetSpawnMob.dataManager = new EntityDataManager(null);

        return packetSpawnMob;
    }

    public static SPacketSpawnObject createPacketSpawnObject(int entityID, UUID entityUUID,
                                                             int objectType, int objectData,
                                                             double posX, double posY, double posZ,
                                                             byte yaw, byte pitch,
                                                             short velX, short velY, short velZ) {
        SPacketSpawnObject packetSpawnObject = new SPacketSpawnObject();
        packetSpawnObject.entityId = entityID;
        packetSpawnObject.uniqueId = entityUUID;
        packetSpawnObject.type = objectType;
        packetSpawnObject.data = objectData;
        packetSpawnObject.x = posX;
        packetSpawnObject.y = posY;
        packetSpawnObject.z = posZ;
        packetSpawnObject.yaw = yaw;
        packetSpawnObject.pitch = pitch;
        packetSpawnObject.speedX = velX;
        packetSpawnObject.speedY = velY;
        packetSpawnObject.speedZ = velZ;

        return packetSpawnObject;
    }

    private static final Map<EntityType, Integer> OBJECT_TYPE_TO_ID = new HashMap<>();

    static {
        registerObjectMappings();
    }

    private static void registerObjectMappings() {
        OBJECT_TYPE_TO_ID.put(EntityTypes.BOAT, 1);
        OBJECT_TYPE_TO_ID.put(EntityTypes.ITEM, 2);
        OBJECT_TYPE_TO_ID.put(EntityTypes.AREA_EFFECT_CLOUD, 3);
        OBJECT_TYPE_TO_ID.put(EntityTypes.CHESTED_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.COMMANDBLOCK_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.FURNACE_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.HOPPER_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.MOB_SPAWNER_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.RIDEABLE_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.TNT_MINECART, 10);
        OBJECT_TYPE_TO_ID.put(EntityTypes.PRIMED_TNT, 50);
        OBJECT_TYPE_TO_ID.put(EntityTypes.ENDER_CRYSTAL, 51);
        OBJECT_TYPE_TO_ID.put(EntityTypes.TIPPED_ARROW, 60);
        OBJECT_TYPE_TO_ID.put(EntityTypes.SNOWBALL, 61);
        OBJECT_TYPE_TO_ID.put(EntityTypes.EGG, 62);
        OBJECT_TYPE_TO_ID.put(EntityTypes.FIREBALL, 63);
        OBJECT_TYPE_TO_ID.put(EntityTypes.SMALL_FIREBALL, 64);
        OBJECT_TYPE_TO_ID.put(EntityTypes.ENDER_PEARL, 65);
        OBJECT_TYPE_TO_ID.put(EntityTypes.WITHER_SKULL, 66);
        OBJECT_TYPE_TO_ID.put(EntityTypes.SHULKER_BULLET, 67);
        OBJECT_TYPE_TO_ID.put(EntityTypes.FALLING_BLOCK, 70);
        OBJECT_TYPE_TO_ID.put(EntityTypes.ITEM_FRAME, 71);
        OBJECT_TYPE_TO_ID.put(EntityTypes.EYE_OF_ENDER, 72);
        OBJECT_TYPE_TO_ID.put(EntityTypes.SPLASH_POTION, 73);
        OBJECT_TYPE_TO_ID.put(EntityTypes.THROWN_EXP_BOTTLE, 75);
        OBJECT_TYPE_TO_ID.put(EntityTypes.FIREWORK, 76);
        OBJECT_TYPE_TO_ID.put(EntityTypes.LEASH_HITCH, 77);
        OBJECT_TYPE_TO_ID.put(EntityTypes.ARMOR_STAND, 78);
        OBJECT_TYPE_TO_ID.put(EntityTypes.FISHING_HOOK, 90);
        OBJECT_TYPE_TO_ID.put(EntityTypes.SPECTRAL_ARROW, 91);
        OBJECT_TYPE_TO_ID.put(EntityTypes.DRAGON_FIREBALL, 93);
    }

    public static int getObjectID(EntityType entityType) {
        Integer id = OBJECT_TYPE_TO_ID.get(entityType);
        return id == null ? 0 : id;
    }

}
