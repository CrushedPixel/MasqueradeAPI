package eu.crushedpixel.sponge.masquerade.api.utils;

import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;

import java.util.ArrayList;

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

}
