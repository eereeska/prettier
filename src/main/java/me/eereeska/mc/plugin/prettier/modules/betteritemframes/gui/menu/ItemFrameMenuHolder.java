package me.eereeska.mc.plugin.prettier.modules.betteritemframes.gui.menu;

import me.eereeska.mc.plugin.prettier.Prettier;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemFrameMenuHolder implements InventoryHolder {

    private final Prettier plugin;

    private final Inventory inv;
    private final ItemFrame itemFrame;

    public ItemFrameMenuHolder(final Prettier plugin, final ItemFrame itemFrame) {
        this.plugin = plugin;

        this.inv = Bukkit.createInventory(this, 27, Component.text(this.plugin.getLang().getPhrase("modules.bif.menu")));
        this.itemFrame = itemFrame;

        this.updateIcons();
    }

    public final ItemStack toggleVisibilityIcon() {
        final ItemStack item = new ItemStack(this.itemFrame.isVisible() ? Material.ENDER_EYE : Material.ENDER_PEARL);
        final ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.displayName(Component.text(this.plugin.getLang().getPhrase("modules.bif.visibility")));

        final List<Component> lore = new ArrayList<>();

        if (this.itemFrame.isVisible()) {
            lore.add(Component.text(this.plugin.getLang().getPhrase("modules.bif.visible")));
        } else {
            lore.add(Component.text(this.plugin.getLang().getPhrase("modules.bif.invisible")));
        }

        meta.lore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public final ItemStack toggleFixationIcon() {
        final ItemStack item = new ItemStack(this.itemFrame.isFixed() ? Material.BEDROCK : Material.GRASS_BLOCK);
        final ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.displayName(Component.text(this.plugin.getLang().getPhrase("modules.bif.fixation")));

        final List<Component> lore = new ArrayList<>();

        if (this.itemFrame.isVisible()) {
            lore.add(Component.text(this.plugin.getLang().getPhrase("modules.bif.fixed")));
        } else {
            lore.add(Component.text(this.plugin.getLang().getPhrase("modules.bif.unfixed")));
        }

        meta.lore(lore);

        item.setItemMeta(meta);

        return item;
    }

//    public final ItemStack functionsIcon() {
//        final ItemStack item = new ItemStack(Material.REDSTONE);
//        final ItemMeta meta = item.getItemMeta();
//
//        if (meta == null) {
//            return item;
//        }
//
//        meta.setDisplayName(this.plugin.getConfig().getString("phrases.functions"));
//
//        item.setItemMeta(meta);
//
//        return item;
//    }

    public final void updateIcons() {
        this.inv.setItem(12, this.toggleVisibilityIcon());
        this.inv.setItem(14, this.toggleFixationIcon());
//        this.inv.setItem(15, this.functionsIcon());
    }

    public final ItemFrame getItemFrame() {
        return this.itemFrame;
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return this.inv;
    }
}
