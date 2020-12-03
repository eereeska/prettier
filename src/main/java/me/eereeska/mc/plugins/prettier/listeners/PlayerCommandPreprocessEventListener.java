package me.eereeska.mc.plugins.prettier.listeners;

import me.eereeska.mc.plugins.prettier.Prettier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class PlayerCommandPreprocessEventListener implements Listener {

    private final Prettier plugin;

    public PlayerCommandPreprocessEventListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPluginsCommand(PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        final String c = e.getMessage().split(" ")[0].replace("/", "").trim().toLowerCase();

        for (final String disabledCommand : plugin.getConfig().getStringList("disabled-commands")) {
            if (disabledCommand.equalsIgnoreCase(c)) {
                e.setCancelled(true);
                return;
            }
        }

        if (p.hasPermission(plugin.getConfig().getString("permissions.bypass"))) {
            return;
        }

        e.setCancelled(true);

        for (final String group : new ArrayList<>(plugin.getConfig().getConfigurationSection("allowed-commands").getKeys(false))) {
            if (p.hasPermission("group." + group) && plugin.getConfig().getStringList("allowed-commands." + group).contains(c)) {
                e.setCancelled(false);
            }
        }
    }
}
