package me.eereeska.mc.plugin.prettier.modules.betteritemframes;

import me.eereeska.mc.plugin.prettier.Prettier;
import me.eereeska.mc.plugin.prettier.modules.betteritemframes.config.ItemFrameConfig;
import me.eereeska.mc.plugin.prettier.modules.betteritemframes.gui.menu.ItemFrameMenuListener;
import me.eereeska.mc.plugin.prettier.modules.betteritemframes.listeners.ItemFrameListener;
import org.bukkit.plugin.PluginManager;

public class BetterItemFramesModule {

    private final ItemFrameConfig itemFrameConfig;

    public BetterItemFramesModule(final Prettier plugin, final PluginManager pm) {
        this.itemFrameConfig = new ItemFrameConfig(plugin);

        pm.registerEvents(new ItemFrameListener(plugin), plugin);
        pm.registerEvents(new ItemFrameMenuListener(plugin), plugin);

        plugin.getLogger().info("§bBetter ItemFrames Module §7— §aenabled");
    }

    public final ItemFrameConfig getItemFrameConfig() {
        return this.itemFrameConfig;
    }
}
