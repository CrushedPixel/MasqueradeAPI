package eu.crushedpixel.sponge.masquerade.api;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Collection;

public interface Masquerade {

    /**
     * Shows the masquerade to a player.
     * @param player The player to show the masquerade to
     */
    void maskTo(Player player);

    /**
     * Removes the masquerade for a player.
     * @param player The player to remove the masquerade for
     */
    void unmaskTo(Player player);

    /**
     * Removes the masquerade for all deceived players.
     */
    void unmask();

    /**
     * Modifies this masquerade's data.
     * @param key The data key to modify
     * @param value The value to set
     * @param <V> The data type
     * @throws IllegalArgumentException if the key is not supported by this masquerade
     */
    <V> void setData(Key<? extends BaseValue<V>> key, V value);

    /**
     * Returns this masquerade's value for a data key.
     * @param key The data key to retrieve the value for
     * @param <V> The data type
     * @return The value
     * @throws IllegalArgumentException if the key is not supported by this masquerade
     */
    <V> V getData(Key<? extends BaseValue<V>> key);

    /**
     * Returns whether a data key is supported by this masquerade.
     * @param key The key in question
     * @return {@code true} if supported, {@code false} otherwise
     */
    boolean hasKey(Key<?> key);

    /**
     * Returns a collection of all data keys supported by this masquerade.
     * @return Collection of keys
     */
    Collection<Key<?>> getKeys();

    /**
     * Defines whether the server is allowed to modify a key by itself.
     * @param key The key in question
     * @param valueChangeAllowed {@code true} if allowed, {@code false} otherwise
     */
    void setValueChangeAllowed(Key<?> key, boolean valueChangeAllowed);

    /**
     * Returns whether the server is allowed to modify a key by itself.
     * @param key The key in question
     * @return {@code true} if allowed, {@code false} otherwise
     */
    boolean isValueChangeAllowed(Key<?> key);

}
