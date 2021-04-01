package me.eereeska.mc.plugin.prettier;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Lang {

    private final File file;
    private final FileConfiguration config;

    public Lang(final Prettier plugin) {
        this.file = new File(plugin.getDataFolder(), "lang.yml");

        if (!this.file.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.config.setDefaults(YamlConfiguration.loadConfiguration(file));

        plugin.getLogger().info("§9lang.yml §7— §aloaded");
    }

    public final String getPhrase(final String path) {
        return config.getString(path);
    }

    public final String getPhrase(final String path, final String def) {
        return config.getString(path, def);
    }

    public final void sendActionBar(final Player p, final String path) {
        final String phrase = getPhrase(path, "none");

        if (phrase.equalsIgnoreCase("none")) {
            return;
        }

        p.sendActionBar(Component.text(phrase));
    }
}
