package me.eereeska.mc.plugins.prettier.listeners;

import me.eereeska.mc.plugins.prettier.Prettier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Prettier plugin;

    public ChatListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (Prettier.usePlaceholderAPI) {

        }
    }
}