package eu.crushedpixel.sponge.masquerade.api;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.function.Function;

public interface Masquerades {

    void registerDefault(EntityType entityType, Function<Player, Masquerade> function);

    Masquerade fromType(EntityType entityType, Player player);

    Masquerade fallingBlock(Player player, BlockState blockState);

}
