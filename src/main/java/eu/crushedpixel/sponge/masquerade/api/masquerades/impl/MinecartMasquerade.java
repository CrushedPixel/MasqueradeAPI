package eu.crushedpixel.sponge.masquerade.api.masquerades.impl;

import eu.crushedpixel.sponge.masquerade.api.masquerades.ObjectMasquerade;
import net.minecraft.entity.item.EntityMinecart;
import org.spongepowered.api.entity.living.player.Player;

public class MinecartMasquerade extends ObjectMasquerade {

    private static final int MINECART_ID = 10;

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
        super(player, EntityMinecart.class, MINECART_ID, minecartType.getId());
    }

}
