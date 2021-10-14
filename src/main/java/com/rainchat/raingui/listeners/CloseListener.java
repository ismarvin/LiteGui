package com.rainchat.raingui.listeners;


import com.rainchat.raingui.manager.InventoryManager;
import com.rainchat.raingui.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseListener implements Listener {

    InventoryManager inventoryManager;

    public CloseListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            Menu hInventory = this.inventoryManager.getPlayerInventory(player);

            if (hInventory == null) {
                return;
            }

            this.inventoryManager.getPlayerInventoryMap().remove(player.getName());

        }
    }
}
