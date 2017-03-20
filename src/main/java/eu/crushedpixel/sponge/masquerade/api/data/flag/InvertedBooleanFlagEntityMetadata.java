package eu.crushedpixel.sponge.masquerade.api.data.flag;

import eu.crushedpixel.sponge.masquerade.api.masquerades.AbstractMasquerade;
import net.minecraft.network.datasync.EntityDataManager;

public class InvertedBooleanFlagEntityMetadata extends BooleanFlagEntityMetadata {

    public InvertedBooleanFlagEntityMetadata(AbstractMasquerade masquerade, EntityDataManager.DataEntry<Byte> flagDataEntry, int position) {
        super(masquerade, flagDataEntry, position);
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(!value);
    }

    @Override
    public Boolean getValue() {
        return !super.getValue();
    }
}
