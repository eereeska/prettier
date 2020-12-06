package me.eereeska.mc.plugins.prettier;

import me.eereeska.mc.plugins.prettier.commands.PrettierCommand;
import me.eereeska.mc.plugins.prettier.listeners.ChatListener;
import me.eereeska.mc.plugins.prettier.listeners.PlayerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prettier extends JavaPlugin {

    public static boolean usePlaceholderAPI = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final PluginManager pm = getServer().getPluginManager();

        // PAPI

        if (pm.getPlugin("PlaceholderAPI") != null) {
            usePlaceholderAPI = true;
            getLogger().info("§bPlaceholderAPI §rwas detected & successfully hooked");
        } else {
            getLogger().info("§bPlaceholderAPI §rwasn't detected & won't be supported");
        }

        // Listeners & TabCompleters

        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new ChatListener(this), this);

        // Commands

        getCommand("prettier").setExecutor(new PrettierCommand(this));

        getLogger().info("§aEnabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cDisabled");
    }

    public final void sendMessageFromConfigTo(final CommandSender sender, final String path) {
        final String message = getConfig().getString(path, "none");

        if (message == null || message.equalsIgnoreCase("none")) {
            return;
        }

        if (message.startsWith("$CHAT:")) {
            sender.sendMessage(message.replaceFirst("\\$CHAT:", "").trim());
        } else if (message.startsWith("$AB:") || message.startsWith("$ACTIONBAR:")) {
            final String m = message.replaceFirst("\\$AB:", "").replaceFirst("\\$ACTIONBAR:", "").trim();

            if (sender instanceof Player) {
                ((Player) sender).sendActionBar(m);
            } else {
                sender.sendMessage(m);
            }
        }
    }
}