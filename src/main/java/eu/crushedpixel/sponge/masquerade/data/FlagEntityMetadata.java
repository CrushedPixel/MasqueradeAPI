package eu.crushedpixel.sponge.masquerade.data;

import eu.crushedpixel.sponge.masquerade.manipulators.DataManipulator;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;

public class FlagEntityMetadata extends EntityMetadata<Byte, Boolean> {

    private final int position;

    private boolean lastServersideValue;

    public FlagEntityMetadata(DataManipulator dataManipulator, DataEntry<Byte> flagDataEntry, int position) {
        super(dataManipulator, flagDataEntry);
        this.position = position;
    }

    @Override
    public void setValue(Boolean value) {
        dataEntry.setValue(setFlag(this.dataEntry.getValue(), position, value));
        sendValue();
    }

    @Override
    public Boolean getValue() {
        return getFlag(this.dataEntry.getValue(), position);
    }

    @Override
    public DataEntry<Byte> handleOutgoingDataEntry(DataEntry<Byte> dataEntry) {
        if (dataEntry.getKey() != this.dataEntry.getKey()) return null;
        if (overridesPlayerData) return null;

        boolean value = getFlag(dataEntry.getValue(), position);
        // if the value hasn't changed in comparison to the last outgoing EntityMetadata packet,
        // we ignore the value to keep any changes made explicitly using setValue().
        if (value == lastServersideValue) return null;
        lastServersideValue = value;

        // otherwise, update this part only
        this.dataEntry.setValue(setFlag(this.dataEntry.getValue(), position, value));
        return this.dataEntry;
    }

    private byte setFlag(byte value, int position, boolean flag) {
        if (flag) {
            return (byte) (value | 1 << position);
        } else {
            return (byte) (value & ~(1 << position));
        }
    }

    private boolean getFlag(byte value, int position) {
        return (value & 1 << position) != 0;
    }

}
