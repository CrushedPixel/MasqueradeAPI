package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.data.ByteToBooleanMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.manipulators.EntityDataManipulator;
import eu.crushedpixel.sponge.masquerade.api.manipulators.EntityLivingDataManipulator;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.api.masquerades.MobMasquerade;
import net.minecraft.entity.passive.EntityBat;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class BatMasquerade extends MobMasquerade<EntityBat, BatMasquerade.BatDataManipulator> {

    public BatMasquerade(Player player) {
        super(player, EntityBat.class);
    }

    @Override
    protected BatDataManipulator createDataManipulator() {
        return new BatDataManipulator(this);
    }

    public class BatDataManipulator extends EntityLivingDataManipulator<EntityBat> {

        public final EntityMetadata<Byte, Boolean> hanging =
                new ByteToBooleanMetadata(this, EntityBat.HANGING, (byte) 1, (byte) 0, "hanging");

        public BatDataManipulator(Masquerade<EntityBat, ? extends EntityDataManipulator<EntityBat>> masquerade) {
            super(masquerade);
        }

        @Override
        public List<EntityMetadata> getAllEntries() {
            List<EntityMetadata> entries = super.getAllEntries();

            entries.add(hanging);

            return entries;
        }
    }

}
