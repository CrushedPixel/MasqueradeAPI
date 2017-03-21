package eu.crushedpixel.sponge.masquerade.impl.masquerades;

import eu.crushedpixel.sponge.masquerade.impl.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.impl.data.EntityMetadata;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public class LivingMobMasquerade extends MobMasquerade {

    public LivingMobMasquerade(Player player, EntityType entityType) {
        super(player, entityType);
    }

    @Override
    protected void registerKeys() {
        super.registerKeys();

        registerKey(Keys.HEALTH, new EntityMetadata<Float, Double>(this, EntityLivingBase.HEALTH, 1f) {
            @Override
            protected Double convertToExternal(Float value) {
                return value.doubleValue();
            }

            @Override
            protected Float convertToInternal(Double value) {
                return value.floatValue();
            }
        });

        registerKey(Keys.STUCK_ARROWS, new BasicEntityMetadata<>(this, EntityLivingBase.ARROW_COUNT_IN_ENTITY, 0));

        // TODO: register inventory keys that send and intercept Entity Equipment packets
    }
}
