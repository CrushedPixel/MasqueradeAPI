package eu.crushedpixel.sponge.masquerade.impl.masquerades;

import eu.crushedpixel.sponge.masquerade.impl.data.flag.BooleanFlagEntityMetadata;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class WolfMasquerade extends TameableMobMasquerade {

    public WolfMasquerade(Player player) {
        super(player, EntityTypes.WOLF);
    }

    @Override
    protected void registerKeys() {
        super.registerKeys();

        registerKey(Keys.ANGRY, new BooleanFlagEntityMetadata(this, tamedFlags, 1));
    }
}
