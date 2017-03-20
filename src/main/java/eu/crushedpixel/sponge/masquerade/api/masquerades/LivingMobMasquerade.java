package eu.crushedpixel.sponge.masquerade.api.masquerades;

import eu.crushedpixel.sponge.masquerade.api.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public class LivingMobMasquerade<E extends EntityLivingBase> extends MobMasquerade<E> {

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
    }
}
