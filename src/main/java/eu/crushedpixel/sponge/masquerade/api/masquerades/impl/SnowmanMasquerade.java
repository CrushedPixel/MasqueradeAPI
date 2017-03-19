package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.data.ByteToBooleanMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.manipulators.EntityLivingDataManipulator;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.api.masquerades.MobMasquerade;
import net.minecraft.entity.monster.EntitySnowman;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class SnowmanMasquerade extends MobMasquerade<EntitySnowman, SnowmanMasquerade.SnowmanDataManipulator> {

    public SnowmanMasquerade(Player player) {
        super(player, EntitySnowman.class);
    }

    @Override
    protected SnowmanDataManipulator createDataManipulator() {
        return new SnowmanDataManipulator(this);
    }

    public class SnowmanDataManipulator extends EntityLivingDataManipulator<EntitySnowman> {

        public final EntityMetadata<Byte, Boolean> pumpkinEquipped =
                new ByteToBooleanMetadata(this, EntitySnowman.PUMPKIN_EQUIPPED, (byte) 0x10, (byte) 0, "pumpkinEquipped") {

                    // the byte value 0x10 stands for "no pumpkin" so we invert the value conversion

                    @Override
                    protected Byte convert(boolean value) {
                        return super.convert(!value);
                    }

                    @Override
                    public Boolean getValue() {
                        return !super.getValue();
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

}
