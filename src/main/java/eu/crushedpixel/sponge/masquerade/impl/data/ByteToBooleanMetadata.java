package eu.crushedpixel.sponge.masquerade.impl.data;

import eu.crushedpixel.sponge.masquerade.impl.AbstractMasquerade;
import net.minecraft.network.datasync.DataParameter;

public class ByteToBooleanMetadata extends EntityMetadata<Byte, Boolean> {

    private final byte valueTrue;

    public ByteToBooleanMetadata(AbstractMasquerade masquerade, DataParameter<Byte> parameter,
                                 byte valueTrue, Byte initialValue) {
        super(masquerade, parameter, initialValue);
        this.valueTrue = valueTrue;
    }

    @Override
    protected Boolean convertToExternal(Byte value) {
        return value == valueTrue;
    }

    @Override
    protected Byte convertToInternal(Boolean value) {
        return value ? valueTrue : 0;
    }
}
