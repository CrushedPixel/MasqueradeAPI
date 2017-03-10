package eu.crushedpixel.sponge.masquerade.manipulators;

import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import lombok.RequiredArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EntityDataManipulator<E extends Entity> implements DataManipulator {

    private static final int FLAG_ON_FIRE = 0;
    private static final int FLAG_SNEAKING = 1;
    private static final int FLAG_SPRINTING = 3;
    private static final int FLAG_INVISIBLE = 5;
    private static final int FLAG_GLOWING = 6;

    private final DataEntry<Byte> flags = new DataEntry<>(Entity.FLAGS, (byte) 0);
    private final DataEntry<Integer> air = new DataEntry<>(Entity.AIR, 300);
    private final DataEntry<String> customName = new DataEntry<>(Entity.CUSTOM_NAME, "");
    private final DataEntry<Boolean> customNameVisible = new DataEntry<>(Entity.CUSTOM_NAME_VISIBLE, false);
    private final DataEntry<Boolean> silent = new DataEntry<>(Entity.SILENT, false);
    private final DataEntry<Boolean> noGravity = new DataEntry<>(Entity.NO_GRAVITY, false);

    private final Masquerade<E, ? extends EntityDataManipulator<E>> masquerade;

    @Override
    public List<DataEntry<?>> getAllEntries() {
        List<DataEntry<?>> entries = new ArrayList<>();

        entries.add(flags);
        entries.add(air);
        entries.add(customName);
        entries.add(customNameVisible);
        entries.add(silent);
        entries.add(noGravity);

        return entries;
    }

    protected final void sendData(DataEntry data) {
        List<DataEntry<?>> entries = new ArrayList<>();
        entries.add(data);

        sendData(entries);
    }

    protected final void sendData(List<DataEntry<?>> data) {
        SPacketEntityMetadata packetEntityMetadata = new SPacketEntityMetadata();
        packetEntityMetadata.entityId = masquerade.getEntityID();
        packetEntityMetadata.dataManagerEntries = data;

        masquerade.sendToAll(packetEntityMetadata);
    }

    protected void setFlag(int position, boolean value) {
        byte flags = this.flags.getValue();
        if (value) {
            this.flags.setValue((byte) (flags | 1 << position));
        } else {
            this.flags.setValue((byte) (flags & ~(1 << position)));
        }
        sendData(this.flags);
    }

    protected boolean getFlag(int position) {
        byte flags = this.flags.getValue();
        return (flags & 1 << position) != 0;
    }

    public void setOnFire(boolean onFire) {
        setFlag(FLAG_ON_FIRE, onFire);
    }

    public boolean isOnFire() {
        return getFlag(FLAG_ON_FIRE);
    }

    public void setSneaking(boolean sneaking) {
        setFlag(FLAG_SNEAKING, sneaking);
    }

    public boolean isSneaking() {
        return getFlag(FLAG_SNEAKING);
    }

    public void setSprinting(boolean sprinting) {
        setFlag(FLAG_SPRINTING, sprinting);
    }

    public boolean isSprinting() {
        return getFlag(FLAG_SPRINTING);
    }

    public void setInvisible(boolean invisible) {
        setFlag(FLAG_INVISIBLE, invisible);
    }

    public boolean isInvisible() {
        return getFlag(FLAG_INVISIBLE);
    }

    public void setGlowing(boolean glowing) {
        setFlag(FLAG_GLOWING, glowing);
    }

    public boolean isGlowing() {
        return getFlag(FLAG_GLOWING);
    }

    public void setAir(int air) {
        this.air.setValue(air);
        sendData(this.air);
    }

    public int getAir() {
        return this.air.getValue();
    }

    public void setCustomName(String customName) {
        this.customName.setValue(customName);
        sendData(this.customName);
    }

    public String getCustomName() {
        return this.customName.getValue();
    }

    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible.setValue(customNameVisible);
        sendData(this.customNameVisible);
    }

    public boolean getCustomNameVisible() {
        return this.customNameVisible.getValue();
    }

    public void setSilent(boolean silent) {
        this.silent.setValue(silent);
        sendData(this.silent);
    }

    public boolean getSilent() {
        return this.silent.getValue();
    }

    public void setNoGravity(boolean noGravity) {
        this.noGravity.setValue(noGravity);
        sendData(this.noGravity);
    }

    public boolean getNoGravity() {
        return this.noGravity.getValue();
    }


}
