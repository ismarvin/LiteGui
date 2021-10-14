package com.rainchat.raingui;

import com.rainchat.raingui.listeners.ClickListener;
import com.rainchat.raingui.listeners.CloseListener;
import com.rainchat.raingui.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RGui {

    private static boolean isFirst = true;
    private static RGui instance;

    private final Plugin plugin;
    private final InventoryManager inventoryManager;

    private RGui(Plugin plugin) {
        RGui.instance = this;

        this.plugin = plugin;
        inventoryManager = new InventoryManager(plugin);

        if (isFirst) {
            isFirst = false;

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new ClickListener(inventoryManager), plugin);
            pm.registerEvents(new CloseListener(inventoryManager), plugin);
        }
    }

    public static RGui getInstance(Plugin plugin) {
        return isFirst ? new RGui(plugin) : RGui.instance;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
