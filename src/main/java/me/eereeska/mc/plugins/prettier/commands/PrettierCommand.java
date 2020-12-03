package me.eereeska.mc.plugins.prettier.commands;

import me.eereeska.mc.plugins.prettier.Prettier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrettierCommand implements CommandExecutor, TabCompleter {

    private final Prettier plugin;

    public PrettierCommand(final Prettier plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            if (!p.hasPermission(plugin.getConfig().getString("commands.prettier.permission"))) {
                plugin.sendMessageFromConfigTo(sender, "commands.prettier.no-permission-message");
            }
        }

        if (args.length < 1) {
            sender.sendMessage("§5Prettier §7v" + plugin.getDescription().getVersion() + " made by §feereeska");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            plugin.sendMessageFromConfigTo(sender, "messages.config-reload-success");
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("commands.prettier.permission"))) {
            return null;
        }

        final ArrayList<String> list = new ArrayList<>();

        list.add("reload");

        if (list.size() > 0) {
            return list;
        }

        return null;
    }
}