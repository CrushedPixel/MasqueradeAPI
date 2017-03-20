package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.masquerades.LivingMobMasquerade;
import net.minecraft.entity.monster.EntityCreeper;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class CreeperMasquerade extends LivingMobMasquerade {

    public CreeperMasquerade(Player player) {
        super(player, EntityTypes.CREEPER);
    }

    @Override
    protected void registerKeys() {
        super.registerKeys();
        registerKey(Keys.CREEPER_CHARGED, new BasicEntityMetadata<>(this, EntityCreeper.POWERED, false));
    }
}
