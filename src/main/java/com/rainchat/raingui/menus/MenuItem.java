package com.rainchat.raingui.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {


    private final ItemStack itemStack;
    private final Consumer<InventoryClickEvent> inventoryClickEvent;

    public MenuItem(ItemStack itemStack, Consumer<InventoryClickEvent> inventoryClickEvent) {
        this.itemStack = itemStack;
        this.inventoryClickEvent = inventoryClickEvent;
    }



    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }
}
