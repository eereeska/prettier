package me.eereeska.mc.plugins.prettier.listeners;

import me.eereeska.mc.plugins.prettier.Prettier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;

public class PlayerCommandSendEventListener implements Listener {

    private final Prettier plugin;

    public PlayerCommandSendEventListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTab(PlayerCommandSendEvent e) {
        final Player p = e.getPlayer();

        e.getCommands().removeAll(plugin.getConfig().getStringList("disabled-commands"));

        if (p.hasPermission(plugin.getConfig().getString("permissions.bypass"))) {
            return;
        }

        e.getCommands().clear();

        for (final String group : new ArrayList<>(plugin.getConfig().getConfigurationSection("allowed-commands").getKeys(false))) {
            if (p.hasPermission("group." + group)) {
                e.getCommands().addAll(plugin.getConfig().getStringList("allowed-commands." + group));
            }
        }
    }
}
