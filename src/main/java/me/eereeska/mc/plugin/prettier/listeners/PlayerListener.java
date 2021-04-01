package me.eereeska.mc.plugin.prettier.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.eereeska.mc.plugin.prettier.Prettier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    private final Prettier plugin;

    public PlayerListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        String m = plugin.getConfig().getString("player-join-message", "disable");

        if (m.equalsIgnoreCase("disable")) {
            return;
        } else if (m.equalsIgnoreCase("hide")) {
            e.setJoinMessage(null);
            return;
        }

        final Player p = e.getPlayer();

        m = m.replaceAll("@player_name", p.getName());
        m = m.replaceAll("@player_display_ame", p.getDisplayName());
        m = m.replaceAll("@world", p.getWorld().getName());

        if (Prettier.usePlaceholderAPI) {
            m = PlaceholderAPI.setPlaceholders(e.getPlayer(), m);
        }

        e.setJoinMessage(m);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        String m = plugin.getConfig().getString("player-quit-message", "disable");

        if (m.equalsIgnoreCase("disable")) {
            return;
        } else if (m.equalsIgnoreCase("hide")) {
            e.setQuitMessage(null);
            return;
        }

        final Player p = e.getPlayer();

        m = m.replaceAll("@player_name", p.getName());
        m = m.replaceAll("@player_display_ame", p.getDisplayName());
        m = m.replaceAll("@world", p.getWorld().getName());

        if (Prettier.usePlaceholderAPI) {
            m = PlaceholderAPI.setPlaceholders(e.getPlayer(), m);
        }

        e.setQuitMessage(m);
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

    @EventHandler (priority = EventPriority.HIGHEST)
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
