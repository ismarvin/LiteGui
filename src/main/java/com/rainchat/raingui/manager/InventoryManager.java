package com.rainchat.raingui.manager;

import com.rainchat.raingui.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {


    private final Plugin plugin;
    private final Map<String, Menu> playerInventoryMap = new HashMap<>();

    public InventoryManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public Map<String, Menu> getPlayerInventoryMap() {
        return this.playerInventoryMap;
    }

    public Menu getPlayerInventory(String playerName) {
        return this.playerInventoryMap.get(playerName);
    }

    public Menu getPlayerInventory(Player player) {
        return this.getPlayerInventory(player.getName());
    }

    public void setPlayerInventory(String playerName, Menu menu) {
        this.playerInventoryMap.put(playerName, menu);
    }

    public void setPlayerInventory(Player player, Menu menu) {
        this.setPlayerInventory(player.getName(), menu);
    }

}
