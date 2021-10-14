package com.rainchat.raingui.menus;

import com.rainchat.raingui.RGui;
import com.rainchat.raingui.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Menu implements InventoryHolder {

    private final InventoryManager inventoryManager;
    private final Inventory inventory;
    private final List<MenuItem> itemList;
    private final HashMap<Integer, MenuItem> clickableItems = new HashMap<>();

    public Menu(RGui rGui, String name, int size) {
        this.inventoryManager = rGui.getInventoryManager();
        this.inventory = Bukkit.createInventory(this, size * 9, name);
        this.itemList = new ArrayList<>();
    }

    public void open(Player player) {
        player.openInventory(inventory);
        this.inventoryManager.setPlayerInventory(player, this);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    protected void addItem(int slot, MenuItem menuItem) {
        clickableItems.put(slot, menuItem);
    }

    public void guiFill(MenuItem clickableItem) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void setItem(int slot, MenuItem clickableItem) {
        if (clickableItem.getItemStack() == null || clickableItem.getItemStack().getType().equals(Material.AIR)) {
            this.clickableItems.put(slot, new MenuItem(new ItemStack(Material.AIR),inventoryClickEvent -> {}));
            this.inventory.setItem(slot, clickableItem.getItemStack());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.inventory.setItem(slot, clickableItem.getItemStack());
    }

    public MenuItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN) || event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (event.getClickedInventory() == null) return;
            if (!event.getClickedInventory().equals(getInventory())) return;
            event.setCancelled(true);
            MenuItem menuItem = getItem(event.getSlot());
            menuItem.getInventoryClickEvent().accept(event);
            inventory.clear();
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
