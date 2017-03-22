package eu.crushedpixel.sponge.masquerade.api;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.function.Function;

public interface Masquerades {

    /**
     * Registers a function creating a {@link Masquerade}
     * to be applied by {@link Masquerades#fromType(EntityType, Player)}.
     * @param entityType The masquerade's entity type
     * @param function The function returning a masquerade,
     *                 masking the player for that entity type
     */
    void registerDefault(EntityType entityType, Function<Player, Masquerade> function);

    /**
     * Creates a masquerade from an {@link EntityType}.
     * @param entityType The masquerade's entity type
     * @param player The player to be masked
     * @return The masquerade
     */
    Masquerade fromType(EntityType entityType, Player player);

    /**
     * Creates a FallingBlock masquerade from a {@link BlockState}.
     * @param blockState The appearance of the masquerade
     * @param player The player to be masked
     * @return The masquerade
     */
    Masquerade fallingBlock(BlockState blockState, Player player);

}
