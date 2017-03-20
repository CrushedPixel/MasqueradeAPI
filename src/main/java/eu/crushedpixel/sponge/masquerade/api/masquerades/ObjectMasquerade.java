package eu.crushedpixel.sponge.masquerade.api.masquerades;

import eu.crushedpixel.sponge.masquerade.api.utils.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnObject;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ObjectMasquerade<E extends Entity> extends MobMasquerade<E> {

    // the object type
    private final int objectType;

    // object data, for example Minecart type
    private final int objectData;

    protected ObjectMasquerade(Player player, EntityType entityType) {
        this(player, entityType, 0);
    }

    protected ObjectMasquerade(Player player, EntityType entityType, int objectData) {
        super(player, entityType);
        this.objectType = PacketUtils.getObjectID(entityType);
        this.objectData = objectData;
    }


    @Override
    public List<Packet> createSpawnPackets(double posX, double posY, double posZ,
                                           byte yaw, byte pitch, byte headPitch,
                                           short velX, short velY, short velZ) {
        List<Packet> packetList = new ArrayList<>();

        SPacketSpawnObject packetSpawnObject = PacketUtils.createPacketSpawnObject(
                entityID, entityUUID, objectType, objectData,
                posX, posY, posZ, yaw, pitch, velX, velY, velZ);

        packetList.add(packetSpawnObject);
        return packetList;
    }

}
