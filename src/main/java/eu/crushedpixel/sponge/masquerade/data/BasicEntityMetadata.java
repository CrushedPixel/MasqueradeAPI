package eu.crushedpixel.sponge.masquerade.data;

import eu.crushedpixel.sponge.masquerade.manipulators.DataManipulator;
import net.minecraft.network.datasync.DataParameter;

public class BasicEntityMetadata<T> extends EntityMetadata<T, T> {

    public BasicEntityMetadata(DataManipulator dataManipulator, DataParameter<T> key, T initialValue) {
        super(dataManipulator, key, initialValue);
    }

    @Override
    public void setValue(T value) {
        dataEntry.setValue(value);
        sendValue();
    }

    @Override
    public T getValue() {
        return dataEntry.getValue();
    }

}
