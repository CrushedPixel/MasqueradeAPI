package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.manipulators.EntityDataManipulator;
import eu.crushedpixel.sponge.masquerade.api.manipulators.EntityLivingDataManipulator;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.api.masquerades.MobMasquerade;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class ZombieMasquerade extends MobMasquerade<EntityZombie, ZombieMasquerade.ZombieDataManipulator> {

    public ZombieMasquerade(Player player) {
        super(player, EntityZombie.class);
    }

    @Override
    protected ZombieDataManipulator createDataManipulator() {
        return new ZombieDataManipulator(this);
    }

    public class ZombieDataManipulator extends EntityLivingDataManipulator<EntityZombie> {

        public final EntityMetadata<Boolean, Boolean> isChild = new BasicEntityMetadata<>(this, EntityZombie.IS_CHILD, false, "isChild");
        public final EntityMetadata<Boolean, Boolean> armsRaised = new BasicEntityMetadata<>(this, EntityZombie.ARMS_RAISED, false, "armsRaised");

        public ZombieDataManipulator(Masquerade<EntityZombie, ? extends EntityDataManipulator<EntityZombie>> masquerade) {
            super(masquerade);
        }

        @Override
        public List<EntityMetadata> getAllEntries() {
            List<EntityMetadata> entries = super.getAllEntries();

            entries.add(isChild);
            entries.add(armsRaised);

            return entries;
        }
    }

}
