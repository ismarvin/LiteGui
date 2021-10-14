package com.rainchat.raingui.listeners;

import com.rainchat.raingui.manager.InventoryManager;
import com.rainchat.raingui.menus.Menu;
import com.rainchat.raingui.menus.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public class ClickListener implements Listener {

    InventoryManager inventoryManager;

    public ClickListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN) || event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Menu hInventory = this.inventoryManager.getPlayerInventory(player);

            if (hInventory == null) {
                return;
            }

            MenuItem clickableItem = hInventory.getItem(event.getSlot());
            if (clickableItem != null) {
                event.setCancelled(true);
                Consumer<InventoryClickEvent> clickEventConsumer = clickableItem.getInventoryClickEvent();
                if (clickEventConsumer != null) {
                    clickEventConsumer.accept(event);
                }
            }
        }
    }
}
