package com.rainchat.raingui.menus;

import com.rainchat.raingui.utils.general.Item;
import com.rainchat.raingui.utils.placeholder.PlaceholderSupply;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.function.Consumer;

public class LiteMenu implements InventoryHolder, Listener {

    private Inventory inventory;

    private HashMap<Integer, ClickItem> clickableItems = new HashMap<>();
    protected int guiSize;
    private PlaceholderSupply[] globalReplacers;
    private BukkitTask update;

    public LiteMenu(Plugin plugin, String name, int size) {
        this.inventory = Bukkit.createInventory(this, size * 9, name);
        this.clickableItems = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void setGlobalReplacers(PlaceholderSupply<?>... replacementSource) {
        this.globalReplacers = replacementSource;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }


    public void close(Player player) {
        player.closeInventory();
    }

    protected void addItem(int slot, ClickItem clickItem) {
        clickableItems.put(slot, clickItem);
    }

    public void guiFill(ClickItem clickableItem) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void setItem(int slot, ClickItem clickableItem) {
        if (clickableItem.getItemStack() == null || clickableItem.getItemStack().getType().equals(Material.AIR)) {
            this.clickableItems.put(slot, new ClickItem(new Item().material(Material.AIR), inventoryClickEvent -> {}));
            this.inventory.setItem(slot, clickableItem.getItemStack());
            return;
        }
        this.clickableItems.put(slot, clickableItem);
        this.inventory.setItem(slot, clickableItem.getItemStack());
    }

    public ClickItem getItem(int slot) {
        return this.clickableItems.getOrDefault(slot, null);
    }

    public void startUpdate(BukkitTask task) {
        if (update != null) {
            update.cancel();
        }
        this.update = task;
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

            ClickItem clickableItem = getItem(event.getSlot());
            if (clickableItem != null) {
                event.setCancelled(true);
                Consumer<InventoryClickEvent> clickEventConsumer = clickableItem.getInventoryClickEvent();
                if (clickEventConsumer != null) {
                    clickEventConsumer.accept(event);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(getInventory())) {
            if (update != null) {
                update.cancel();
            }
            HandlerList.unregisterAll(this);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
