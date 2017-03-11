package eu.crushedpixel.sponge.masquerade.plugin;

import eu.crushedpixel.sponge.masquerade.manipulators.EntityDataManipulator;
import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;
import eu.crushedpixel.sponge.masquerade.masquerades.impl.MinecartMasquerade;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Plugin(id = MasqueradePlugin.ID, dependencies = { @Dependency(id = "packetgate") })
public class MasqueradePlugin {

    public static final String ID = "masquerade";

    private Map<UUID, Masquerade<?, ? extends EntityDataManipulator<?>>> masquerades = new ConcurrentHashMap<>();

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

                    MinecartMasquerade masquerade = new MinecartMasquerade((Player) source, MinecartMasquerade.MinecartType.TNT);
                    Sponge.getServer().getOnlinePlayers().forEach(player -> {
                                if (player.getUniqueId().equals(((Player) source).getUniqueId())) return;
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

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor((source, context) -> {
                    if (!(source instanceof Player)) {
                        return CommandResult.empty();
                    }

                    UUID playerUUID = ((Player) source).getUniqueId();
                    if (!masquerades.containsKey(playerUUID)) {
                        return CommandResult.empty();
                    }

                    EntityDataManipulator<?> dataManipulator = masquerades.get(playerUUID).getDataManipulator();
                    dataManipulator.onFire.setValue(!dataManipulator.onFire.getValue());

                    return CommandResult.success();
                }).build(), "burn");
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        masquerades.forEach((uuid, masquerade) -> {
            if (uuid.equals(event.getTargetEntity().getUniqueId())) return;
            masquerade.maskTo(event.getTargetEntity());
        });
    }

}
