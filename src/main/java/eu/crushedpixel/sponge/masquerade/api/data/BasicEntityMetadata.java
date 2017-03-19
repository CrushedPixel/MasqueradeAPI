package eu.crushedpixel.sponge.masquerade.api.data;

import eu.crushedpixel.sponge.masquerade.api.manipulators.DataManipulator;
import net.minecraft.network.datasync.DataParameter;

public class BasicEntityMetadata<T> extends EntityMetadata<T, T> {

    public BasicEntityMetadata(DataManipulator dataManipulator, DataParameter<T> key, T initialValue, String name) {
        super(dataManipulator, key, initialValue, name);
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
