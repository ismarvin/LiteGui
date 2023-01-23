package com.rainchat.raingui.menus;

import com.rainchat.raingui.utils.general.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickItem {


    private final Item item;
    private Consumer<InventoryClickEvent> inventoryClickEvent;

    public ClickItem(Item item, Consumer<InventoryClickEvent> inventoryClickEvent) {
        this.item = item;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    public void setInventoryClickEvent(Consumer<InventoryClickEvent> inventoryClickEvent) {
        this.inventoryClickEvent = inventoryClickEvent;
    }

    public ItemStack getItemStack() {
        return item.build();
    }

    public Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }
}
