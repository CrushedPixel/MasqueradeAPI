package eu.crushedpixel.sponge.masquerade.manipulators;

import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;

import java.util.List;

public class EntityMobDataManipulator<E extends EntityLivingBase> extends EntityDataManipulator<E> {

    private static final int FLAG_ELYTRA_FLYING = 7;

    private final DataEntry<Byte> handStates = new DataEntry<>(EntityLivingBase.HAND_STATES, (byte) 0);
    private final DataEntry<Float> health = new DataEntry<>(EntityLivingBase.HEALTH, 1f);
    private final DataEntry<Integer> potionEffectColor = new DataEntry<>(EntityLivingBase.POTION_EFFECTS, 0);
    private final DataEntry<Boolean> hideParticles = new DataEntry<>(EntityLivingBase.HIDE_PARTICLES, false);
    private final DataEntry<Integer> arrowCountInEntity = new DataEntry<>(EntityLivingBase.ARROW_COUNT_IN_ENTITY, 0);

    public EntityMobDataManipulator(Masquerade<E, ? extends EntityDataManipulator<E>> masquerade) {
        super(masquerade);
    }

    @Override
    public List<DataEntry<?>> getAllEntries() {
        List<DataEntry<?>> entries = super.getAllEntries();

        entries.add(handStates);
        entries.add(health);
        entries.add(potionEffectColor);
        entries.add(hideParticles);
        entries.add(arrowCountInEntity);

        return entries;
    }

    public void setElytraFlying(boolean elytraFlying) {
        setFlag(FLAG_ELYTRA_FLYING, elytraFlying);
    }

    public boolean isElytraFlying() {
        return getFlag(FLAG_ELYTRA_FLYING);
    }

    public void setHealth(float health) {
        this.health.setValue(health);
        sendData(this.health);
    }

    public float getHealth() {
        return this.health.getValue();
    }

    public void setPotionEffectColor(int potionEffectColor) {
        this.potionEffectColor.setValue(potionEffectColor);
        sendData(this.potionEffectColor);
    }

    public int getPotionEffectColor() {
        return this.potionEffectColor.getValue();
    }

    public void setParticlesHidden(boolean hideParticles) {
        this.hideParticles.setValue(hideParticles);
        sendData(this.hideParticles);
    }

    public boolean particlesHidden() {
        return this.hideParticles.getValue();
    }

    public void setArrowCountInEntity(int arrowCountInEntity) {
        this.arrowCountInEntity.setValue(arrowCountInEntity);
        sendData(this.arrowCountInEntity);
    }

    public int getArrowCountInEntity() {
        return this.arrowCountInEntity.getValue();
    }

}
