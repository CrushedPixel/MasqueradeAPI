package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.masquerades.ObjectMasquerade;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

public class MinecartMasquerade extends ObjectMasquerade {

    public enum MinecartType {

        NORMAL(0), CHEST(1), FURNACE(2), TNT(3), SPAWNER(4), HOPPER(5), COMMAND_BLOCK(6);

        private final int id;

        MinecartType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public MinecartMasquerade(Player player, MinecartType minecartType) {
        super(player, EntityTypes.RIDEABLE_MINECART, minecartType.getId());
    }

}
