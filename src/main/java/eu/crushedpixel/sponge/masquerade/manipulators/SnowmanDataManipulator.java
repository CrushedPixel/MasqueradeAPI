package eu.crushedpixel.sponge.masquerade.manipulators;

import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;

import java.util.List;

public class SnowmanDataManipulator extends EntityMobDataManipulator<EntitySnowman> {

    private final DataEntry<Byte> pumpkinEquipped = new DataEntry<>(EntitySnowman.PUMPKIN_EQUIPPED, (byte) 0);

    public SnowmanDataManipulator(Masquerade<EntitySnowman, SnowmanDataManipulator> masquerade) {
        super(masquerade);
    }

    public boolean isPumpkinEquipped() {
        return pumpkinEquipped.getValue() == 0x10;
    }

    public void setPumpkinEquipped(boolean pumpkinEquipped) {
        this.pumpkinEquipped.setValue(pumpkinEquipped ? (byte) 0x10 : 0);
        sendData(this.pumpkinEquipped);
    }

    @Override
    public List<DataEntry<?>> getAllEntries() {
        List<DataEntry<?>> entries = super.getAllEntries();

        entries.add(pumpkinEquipped);

        return entries;
    }
}
