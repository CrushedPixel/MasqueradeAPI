package eu.crushedpixel.sponge.masquerade.api.data.flag;

import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.masquerades.AbstractMasquerade;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;

public abstract class FlagEntityMetadata<T> extends EntityMetadata<Byte, T> {

    protected final int position;

    private boolean lastServersideValue;

    public FlagEntityMetadata(AbstractMasquerade masquerade, DataEntry<Byte> flagDataEntry, int position) {
        super(masquerade, flagDataEntry);
        this.position = position;
    }

    @Override
    public DataEntry<Byte> handleOutgoingDataEntry(DataEntry<Byte> dataEntry) {
        if (dataEntry.getKey() != this.dataEntry.getKey()) return null;
        if (!allowValueChange) return null;

        boolean value = getFlag(dataEntry.getValue(), position);
        // if the value hasn't changed in comparison to the last outgoing EntityMetadata packet,
        // we ignore the value to keep any changes made explicitly using setValue().
        if (value == lastServersideValue) return null;
        lastServersideValue = value;

        // otherwise, update this part only
        this.dataEntry.setValue(setFlag(this.dataEntry.getValue(), position, value));
        return this.dataEntry;
    }

    protected byte setFlag(byte value, int position, boolean flag) {
        if (flag) {
            return (byte) (value | 1 << position);
        } else {
            return (byte) (value & ~(1 << position));
        }
    }

    protected boolean getFlag(byte value, int position) {
        return (value & 1 << position) != 0;
    }

}
