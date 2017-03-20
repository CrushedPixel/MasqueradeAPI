package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.masquerades.ObjectMasquerade;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class MinecartMasquerade extends ObjectMasquerade {

    public enum MinecartType {

        NORMAL(0, EntityTypes.RIDEABLE_MINECART), CHEST(1, EntityTypes.CHESTED_MINECART),
        FURNACE(2, EntityTypes.FURNACE_MINECART), TNT(3, EntityTypes.TNT_MINECART),
        SPAWNER(4, EntityTypes.MOB_SPAWNER_MINECART), HOPPER(5, EntityTypes.HOPPER_MINECART),
        COMMAND_BLOCK(6, EntityTypes.COMMANDBLOCK_MINECART);

        private final int id;
        private final EntityType entityType;

        MinecartType(int id, EntityType entityType) {
            this.id = id;
            this.entityType = entityType;
        }

        public int getId() {
            return id;
        }

        public EntityType getEntityType() {
            return entityType;
        }

        public static MinecartType fromEntityType(EntityType entityType) {
            for (MinecartType type : values()) {
                if (type.getEntityType() == entityType) {
                    return type;
                }
            }

            return NORMAL;
        }
    }

    public MinecartMasquerade(Player player, EntityType entityType) {
        this(player, MinecartType.fromEntityType(entityType));
    }

    public MinecartMasquerade(Player player, MinecartType minecartType) {
        super(player, minecartType.getEntityType(), minecartType.getId());
    }

}
