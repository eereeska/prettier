package me.eereeska.mc.plugin.prettier;

import me.eereeska.mc.plugin.prettier.modules.betteritemframes.BetterItemFramesModule;
import me.eereeska.mc.plugin.prettier.modules.core.commands.PrettierCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prettier extends JavaPlugin {

    private Lang lang;

    public static boolean usePlaceholderAPI = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("§9config.yml §7— §aloaded");

        lang = new Lang(this);

        final PluginManager pm = getServer().getPluginManager();

        // PAPI

        if (pm.getPlugin("PlaceholderAPI") != null) {
            usePlaceholderAPI = true;
            getLogger().info("§bPlaceholderAPI §7— §aenabled");
        } else {
            getLogger().info("§bPlaceholderAPI §7— §cmissing");
        }

        // Modules

        if (getConfig().getBoolean("modules.betterItemFrames", true)) {
            new BetterItemFramesModule(this, pm);
        }

        // Listeners & TabCompleters

//        pm.registerEvents(new PlayerListener(this), this);
//        pm.registerEvents(new ChatListener(this), this);

        // Commands

        getCommand("prettier").setExecutor(new PrettierCommand(this));

        getLogger().info("§dReady to accept players on your server");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cDisabled");
    }

    public final Lang getLang() {
        return this.lang;
    }
}