package eu.crushedpixel.sponge.masquerade.masquerades;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnObject;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ObjectMasquerade<E extends Entity> extends Masquerade<E> {

    // the object type
    private final int objectType;

    public ObjectMasquerade(Player player, Class<? extends E> entityClass) {
        super(player, entityClass);
        this.objectType = 0;
    }

    @Override
    protected List<Packet> createSpawnPackets(double posX, double posY, double posZ,
                                byte yaw, byte pitch, byte headPitch,
                                short velX, short velY, short velZ) {
        List<Packet> packetList = new ArrayList<>();

        SPacketSpawnObject packetSpawnObject = new SPacketSpawnObject();
        packetSpawnObject.entityId = this.entityID;
        packetSpawnObject.uniqueId = this.entityUUID;
        packetSpawnObject.type = this.objectType;
        packetSpawnObject.x = posX;
        packetSpawnObject.y = posY;
        packetSpawnObject.z = posZ;
        packetSpawnObject.yaw = yaw;
        packetSpawnObject.pitch = pitch;
        packetSpawnObject.speedX = velX;
        packetSpawnObject.speedY = velY;
        packetSpawnObject.speedZ = velZ;

        packetList.add(packetSpawnObject);
        return packetList;
    }

}
