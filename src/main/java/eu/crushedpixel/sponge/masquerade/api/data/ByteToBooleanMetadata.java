package eu.crushedpixel.sponge.masquerade.api.data;

import eu.crushedpixel.sponge.masquerade.api.manipulators.DataManipulator;
import net.minecraft.network.datasync.DataParameter;

public class ByteToBooleanMetadata extends EntityMetadata<Byte, Boolean> {

    private final byte valueTrue;

    public ByteToBooleanMetadata(DataManipulator dataManipulator, DataParameter<Byte> key,
                                 byte valueTrue, Byte initialValue, String name) {
        super(dataManipulator, key, initialValue, name);
        this.valueTrue = valueTrue;
    }

    @Override
    public void setValue(Boolean value) {
        dataEntry.setValue(convert(value));
        sendValue();
    }

    @Override
    public Boolean getValue() {
        return convert(dataEntry.getValue());
    }

    protected Byte convert(boolean value) {
        return value ? valueTrue : 0;
    }

    protected boolean convert(Byte value) {
        return value == valueTrue;
    }
}
