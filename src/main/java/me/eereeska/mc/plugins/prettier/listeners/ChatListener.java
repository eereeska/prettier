package me.eereeska.mc.plugins.prettier.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.eereeska.mc.plugins.prettier.Prettier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener implements Listener {

    private final Prettier plugin;

    public ChatListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (!plugin.getConfig().getBoolean("chat-format.enabled", false)) {
            return;
        }

        final Player p = e.getPlayer();
        final String originalMessage = e.getMessage().trim();

        String formattedMessage = originalMessage;

        // TODO: mentions

        final ArrayList<String> formats = new ArrayList<>(plugin.getConfig().getConfigurationSection("chat-format.formats").getKeys(false));

        if (formats.size() < 1) {
            plugin.getLogger().info("§eChat format is enabled, but there is no available 'formats' in the config file");
            return;
        }

        String formatTitle = null;

        for (final String f : formats) {
            final String prefix = plugin.getConfig().getString("chat-format.formats." + f + ".prefix");

            if (prefix == null) {
                continue;
            }

            if (originalMessage.startsWith(prefix)) {
                formatTitle = f;
            }
        }

        if (plugin.getConfig().contains("chat-format.formats." + formatTitle + ".send-permission")) {
            if (!p.hasPermission(plugin.getConfig().getString("chat-format.formats." + formatTitle + ".send-permission"))) {
                if (plugin.getConfig().contains("chat-format.formats." + formatTitle + ".send-no-permission")) {
                    plugin.sendMessageFromConfigTo(p, "chat-format.formats." + formatTitle + ".send-no-permission");
                    e.setCancelled(true);
                    return;
                }
            }
        }

        if (plugin.getConfig().contains("chat-format.formats." + formatTitle + ".receive-permission")) {
            final String finalFormatTitle = formatTitle;
            e.getRecipients().removeIf(player -> !player.hasPermission(plugin.getConfig().getString("chat-format.formats." + finalFormatTitle + ".receive-permission")));
        }

        formattedMessage = formattedMessage.replaceFirst(plugin.getConfig().getString("chat-format.formats." + formatTitle + ".prefix", ""), "");

        final int range = plugin.getConfig().getInt("chat-format.formats." + formatTitle + ".range", 50);

        if (range == -1) {
            e.getRecipients().removeIf(player -> !player.getWorld().equals(p.getWorld()));
        } else if (range >= 0) {
            e.getRecipients().removeIf(player -> player.getLocation().distance(p.getLocation()) > range);
        }

        String format = plugin.getConfig().getString("chat-format.formats." + formatTitle + ".format");

        if (format != null) {
            format = format.replaceAll("@player_name", p.getName());
            format = format.replaceAll("@player_display_name", p.getDisplayName());
            format = format.replaceAll("@world", p.getWorld().getName());

            if (Prettier.usePlaceholderAPI) {
                format = PlaceholderAPI.setPlaceholders(p, format);
            }

            format = format.replaceAll("@message", formattedMessage.trim());

            e.setFormat(format);
        }
    }
}