package eu.crushedpixel.sponge.masquerade.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.manipulators.SnowmanDataManipulator;
import eu.crushedpixel.sponge.masquerade.masquerades.MobMasquerade;
import net.minecraft.entity.monster.EntitySnowman;
import org.spongepowered.api.entity.living.player.Player;

public class SnowmanMasquerade extends MobMasquerade<EntitySnowman, SnowmanDataManipulator> {

    public SnowmanMasquerade(Player player) {
        super(player, EntitySnowman.class);
    }

    @Override
    protected SnowmanDataManipulator createDataManipulator() {
        return new SnowmanDataManipulator(this);
    }

}
