package me.eereeska.mc.plugin.prettier.modules.betteritemframes.listeners;

import me.eereeska.mc.plugin.prettier.modules.betteritemframes.gui.menu.ItemFrameMenuHolder;
import me.eereeska.mc.plugin.prettier.Prettier;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;

public class ItemFrameListener implements Listener {

    private final Prettier plugin;

    public ItemFrameListener(final Prettier plugin) {
        this.plugin = plugin;
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onItemFrameBreak(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof ItemFrame)) {
            return;
        }

        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }

        final Player p = e.getPlayer();
        final ItemFrame itemFrame = (ItemFrame) e.getRightClicked();

        if (p.isSneaking()) {
            if (p.hasPermission("prettier.bif.use")) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    if (itemFrame.getItem().getType().equals(Material.AIR)) {
                        if (!plugin.getConfig().getBoolean("modules.betterItemFrames.options.allowEmptyFrames", false)) {
                            if (!p.isOp()) {
                                return;
                            }
                        }
                    }

                    e.setCancelled(true);
                    p.openInventory(new ItemFrameMenuHolder(plugin, itemFrame).getInventory());
                }
            }
        } else {
            final Block blockBehind = itemFrame.getLocation().getBlock().getRelative(itemFrame.getFacing().getOppositeFace());
            final BlockState blockBehindState = blockBehind.getState();

            if (blockBehindState instanceof InventoryHolder && !itemFrame.isVisible()) {
                e.setCancelled(true);
                p.openInventory(((InventoryHolder) blockBehindState).getInventory());
            }
        }
    }
}
