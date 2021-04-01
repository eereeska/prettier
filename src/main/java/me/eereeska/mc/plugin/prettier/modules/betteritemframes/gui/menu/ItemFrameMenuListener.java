package me.eereeska.mc.plugin.prettier.modules.betteritemframes.gui.menu;

import me.eereeska.mc.plugin.prettier.Prettier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemFrameMenuListener implements Listener {

    private final Prettier plugin;

    public ItemFrameMenuListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onIconClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        final Player p = (Player) e.getWhoClicked();

        if (!(e.getView().getTopInventory().getHolder() instanceof ItemFrameMenuHolder)) {
            return;
        }

        e.setCancelled(true);

        final ItemStack clicked = e.getCurrentItem();

        if (clicked == null) {
            return;
        }

        final ItemFrameMenuHolder inventory = (ItemFrameMenuHolder) e.getView().getTopInventory().getHolder();

        if (clicked.equals(inventory.toggleVisibilityIcon())) {
            if (!plugin.getConfig().getBoolean("modules.betterItemFrames.visibility.allowEmptyFrames", false) && inventory.getItemFrame().getItem().getType().equals(Material.AIR)) {
                plugin.getLang().sendActionBar(p, "modules.bif.cant-be-empty");
                return;
            }

            inventory.getItemFrame().setVisible(!inventory.getItemFrame().isVisible());
            inventory.updateIcons();
        } else if (clicked.equals(inventory.toggleFixationIcon())) {
            if (!plugin.getConfig().getBoolean("modules.betterItemFrames.fixation.allowEmptyFrames", false) && inventory.getItemFrame().getItem().getType().equals(Material.AIR)) {
                plugin.getLang().sendActionBar(p, "modules.bif.cant-be-empty");
                return;
            }

            inventory.getItemFrame().setFixed(!inventory.getItemFrame().isFixed());
            inventory.updateIcons();
        }
    }
}
