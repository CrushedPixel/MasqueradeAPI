package eu.crushedpixel.sponge.masquerade.masquerades;

import eu.crushedpixel.sponge.masquerade.manipulators.EntityDataManipulator;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnObject;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectMasquerade<E extends Entity> extends Masquerade<E, EntityDataManipulator<E>> {

    // the object type
    private final int objectType;

    // object data, for example Minecart type
    private final int objectData;

    protected ObjectMasquerade(Player player, Class<? extends E> entityClass, int objectType) {
        this(player, entityClass, objectType, 0);
    }

    protected ObjectMasquerade(Player player, Class<? extends E> entityClass, int objectType, int objectData) {
        super(player, entityClass);
        this.objectType = objectType;
        this.objectData = objectData;
    }

    @Override
    protected EntityDataManipulator<E> createDataManipulator() {
        return new EntityDataManipulator<>(this);
    }

    @Override
    public List<Packet> createSpawnPackets(double posX, double posY, double posZ,
                                           byte yaw, byte pitch, byte headPitch,
                                           short velX, short velY, short velZ) {
        List<Packet> packetList = new ArrayList<>();

        SPacketSpawnObject packetSpawnObject = new SPacketSpawnObject();
        packetSpawnObject.entityId = this.entityID;
        packetSpawnObject.uniqueId = this.entityUUID;
        packetSpawnObject.type = this.objectType;
        packetSpawnObject.data = this.objectData;
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
