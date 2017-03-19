package eu.crushedpixel.sponge.masquerade.api.manipulators;

import eu.crushedpixel.sponge.masquerade.api.data.FlagEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.api.data.BasicEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public class EntityMobDataManipulator<E extends EntityLivingBase> extends EntityDataManipulator<E> {

    private static final int FLAG_ELYTRA_FLYING = 7;

    public final EntityMetadata<Byte, Boolean> elytraFlying = new FlagEntityMetadata(this, flags, FLAG_ELYTRA_FLYING);
    public final EntityMetadata<Byte, Byte> handStates = new BasicEntityMetadata<>(this, EntityLivingBase.HAND_STATES, (byte) 0);
    public final BasicEntityMetadata<Float> health = new BasicEntityMetadata<>(this, EntityLivingBase.HEALTH, 1f);
    public final BasicEntityMetadata<Integer> potionEffectColor = new BasicEntityMetadata<>(this, EntityLivingBase.POTION_EFFECTS, 0);
    public final BasicEntityMetadata<Boolean> hideParticles = new BasicEntityMetadata<>(this, EntityLivingBase.HIDE_PARTICLES, false);
    public final BasicEntityMetadata<Integer> arrowCountInEntity = new BasicEntityMetadata<>(this, EntityLivingBase.ARROW_COUNT_IN_ENTITY, 0);

    public EntityMobDataManipulator(Masquerade<E, ? extends EntityDataManipulator<E>> masquerade) {
        super(masquerade);
    }

    @Override
    public List<EntityMetadata> getAllEntries() {
        List<EntityMetadata> entries = super.getAllEntries();

        entries.add(elytraFlying);
        entries.add(handStates);
        entries.add(health);
        entries.add(potionEffectColor);
        entries.add(hideParticles);
        entries.add(arrowCountInEntity);

        return entries;
    }

}
