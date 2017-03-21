package eu.crushedpixel.sponge.masquerade.impl.masquerades;

import eu.crushedpixel.sponge.masquerade.impl.data.flag.BooleanFlagEntityMetadata;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

public class TameableMobMasquerade extends LivingMobMasquerade {

    protected DataEntry<Byte> tamedFlags;

    public TameableMobMasquerade(Player player, EntityType entityType) {
        super(player, entityType);
    }

    @Override
    protected void registerKeys() {
        super.registerKeys();

        tamedFlags = new DataEntry<>(EntityTameable.TAMED, (byte) 0);

        registerKey(Keys.IS_SITTING, new BooleanFlagEntityMetadata(this, tamedFlags, 0));
    }
}
