package eu.crushedpixel.sponge.masquerade.manipulators;

import eu.crushedpixel.sponge.masquerade.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import net.minecraft.entity.monster.EntitySnowman;

import java.util.List;

public class SnowmanDataManipulator extends EntityMobDataManipulator<EntitySnowman> {

    public final EntityMetadata<Byte, Boolean> pumpkinEquipped = new EntityMetadata<Byte, Boolean>(this, EntitySnowman.PUMPKIN_EQUIPPED, (byte) 0) {
        @Override
        public void setValue(Boolean value) {
            dataEntry.setValue(value ? (byte) 0 : 0x10);
            sendValue();
        }

        @Override
        public Boolean getValue() {
            return dataEntry.getValue() == 0;
        }
    };

    public SnowmanDataManipulator(Masquerade<EntitySnowman, SnowmanDataManipulator> masquerade) {
        super(masquerade);
    }

    @Override
    public List<EntityMetadata> getAllEntries() {
        List<EntityMetadata> entries = super.getAllEntries();

        entries.add(pumpkinEquipped);

        return entries;
    }
}
