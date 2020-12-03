package me.eereeska.mc.plugins.prettier;

import me.eereeska.mc.plugins.prettier.commands.PrettierCommand;
import me.eereeska.mc.plugins.prettier.listeners.PlayerCommandPreprocessEventListener;
import me.eereeska.mc.plugins.prettier.listeners.PlayerCommandSendEventListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prettier extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Listeners & TabCompleters

        final PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerCommandSendEventListener(this), this);
        pm.registerEvents(new PlayerCommandPreprocessEventListener(this), this);

        // Commands

        getCommand(getConfig().getString("commands.prettier.label", "prettier")).setExecutor(new PrettierCommand(this));

        new Metrics(this, 9548);

        getLogger().info("§aEnabled");
    }

    @Override
    public void onDisable() {
        if (getServer().isStopping()) {
            getLogger().info("§cDisabled (server is stopping)");
        } else {
            getLogger().info("§cDisabled");
        }
    }

    public final void sendMessageFromConfigTo(final CommandSender sender, final String path) {
        final String message = getConfig().getString(path, "none");

        if (message == null || message.equalsIgnoreCase("none")) {
            return;
        }

        if (message.startsWith("%CHAT%")) {
            sender.sendMessage(message.replaceFirst("%CHAT%", "").trim());
        } else if (message.startsWith("%AB%") || message.startsWith("%ACTIONBAR%")) {
            final String m = message.replaceFirst("%AB%", "").replaceFirst("%ACTIONBAR%", "").trim();

            if (sender instanceof Player) {
                ((Player) sender).sendActionBar(m);
            } else {
                sender.sendMessage(m);
            }
        }
    }
}