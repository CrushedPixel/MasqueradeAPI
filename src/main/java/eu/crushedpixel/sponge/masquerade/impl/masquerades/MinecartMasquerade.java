package eu.crushedpixel.sponge.masquerade.impl.masquerades;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class MinecartMasquerade extends ObjectMasquerade {

    private static int getMinecartType(EntityType entityType) {
        if (entityType == EntityTypes.RIDEABLE_MINECART) return 0;
        if (entityType == EntityTypes.CHESTED_MINECART) return 1;
        if (entityType == EntityTypes.FURNACE_MINECART) return 2;
        if (entityType == EntityTypes.TNT_MINECART) return 3;
        if (entityType == EntityTypes.MOB_SPAWNER_MINECART) return 4;
        if (entityType == EntityTypes.HOPPER_MINECART) return 5;
        if (entityType == EntityTypes.COMMANDBLOCK_MINECART) return 6;
        return getMinecartType(EntityTypes.RIDEABLE_MINECART);
    }

    public MinecartMasquerade(Player player, EntityType entityType) {
        super(player, entityType, getMinecartType(entityType));
    }

}
