package me.eereeska.mc.plugins.prettier;

import me.eereeska.mc.plugins.prettier.listeners.PlayerCommandPreprocessEventListener;
import me.eereeska.mc.plugins.prettier.listeners.PlayerCommandSendEventListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prettier extends JavaPlugin {

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerCommandSendEventListener(this), this);
        pm.registerEvents(new PlayerCommandPreprocessEventListener(this), this);

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
}