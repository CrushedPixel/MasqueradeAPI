package eu.crushedpixel.sponge.masquerade.plugin;

import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.masquerades.MobMasquerade;
import net.minecraft.entity.monster.EntitySnowman;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Plugin(id = MasqueradePlugin.ID, dependencies = { @Dependency(id = "packetgate") })
public class MasqueradePlugin {

    public static final String ID = "masquerade";

    private Map<UUID, Masquerade> masquerades = new HashMap<>();

    @Listener
    public void onInit(GameInitializationEvent event) {
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor((source, context) -> {
                    if (!(source instanceof Player)) {
                        return CommandResult.empty();
                    }

                    UUID playerUUID = ((Player) source).getUniqueId();
                    if (masquerades.containsKey(playerUUID)) {
                        return CommandResult.empty();
                    }

                    Masquerade masquerade = new MobMasquerade<>((Player) source, EntitySnowman.class);
                    Sponge.getServer().getOnlinePlayers().forEach(player -> {
                                if (player == source) return;
                                masquerade.maskTo(player);
                            }
                    );

                    masquerades.put(playerUUID, masquerade);

                    return CommandResult.success();
                }).build(), "mask");

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor((source, context) -> {
                    if (!(source instanceof Player)) {
                        return CommandResult.empty();
                    }

                    UUID playerUUID = ((Player) source).getUniqueId();
                    if (!masquerades.containsKey(playerUUID)) {
                        return CommandResult.empty();
                    }

                    masquerades.get(playerUUID).unmask();
                    masquerades.remove(playerUUID);

                    return CommandResult.success();
                }).build(), "unmask");
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        masquerades.forEach((uuid, masquerade) -> masquerade.maskTo(event.getTargetEntity()));
    }

    @Listener
    public void onPlayerLeave(ClientConnectionEvent.Disconnect event) {
        masquerades.forEach((uuid, masquerade) -> masquerade.unmaskTo(event.getTargetEntity()));

        Masquerade masquerade = masquerades.get(event.getTargetEntity().getUniqueId());
        if (masquerade != null) {
            masquerade.unmask();
            masquerades.remove(event.getTargetEntity().getUniqueId());
        }
    }

}
