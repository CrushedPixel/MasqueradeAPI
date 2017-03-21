package eu.crushedpixel.sponge.masquerade.api;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Collection;

public interface Masquerade {

    /**
     * Shows the masquerade to the specific player.
     */
    void maskTo(Player player);

    /**
     * Removes the masquerade for the specified player.
     */
    void unmaskTo(Player player);

    /**
     * Removes the masquerade for all deceived players.
     */
    void unmask();

    <V> void setData(Key<? extends BaseValue<V>> key, V value);

    <V> V getData(Key<? extends BaseValue<V>> key);

    <V> boolean hasKey(Key<? extends BaseValue<V>> key);

    Collection<Key<?>> getKeys();

    void setAllowValueChange(Key key, boolean allowValueChange);

    boolean allowsValueChange(Key key);

}
