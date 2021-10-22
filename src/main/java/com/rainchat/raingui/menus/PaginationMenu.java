package com.rainchat.raingui.menus;

import com.rainchat.raingui.utils.general.Item;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginationMenu extends LiteMenu {

    private int page;
    private List<Integer> itemSlots;
    private List<ClickItem> paginationItems;

    public PaginationMenu(Plugin plugin, String name, int size) {
        super(plugin, name, size);
        this.page = 0;
        this.paginationItems = new ArrayList<>();
        this.itemSlots = new ArrayList<>();
    }

    public void nextPage() {
        this.setPage(this.page + 1);
    }

    public void previousPage() {
        this.setPage(this.page - 1);
    }

    public void firstPage() {
        this.setPage(0);
    }

    public void lastPage() {
        this.setPage(this.getLastPage());
    }


    public int getPage() {
        return this.page;
    }

    public int getFirstPage() {
        return 0;
    }


    public List<ClickItem> getPaginationItems() {
        return this.paginationItems;
    }


    public List<Integer> getItemSlots() {
        return this.itemSlots;
    }

    public int getLastPage() {
        int m = (int) Math.ceil((double) getPaginationItems().size() / getItemSlots().size()) - 1;
        return m != -1 ? m : 0;
    }

    public void setPage(int page) {
        if (this.paginationItems.size() == 0) return;
        else if (this.itemSlots.size() == 0) return;

        int oldPage = this.page;
        this.page = page;
        if (!this.updateInventory()) this.page = oldPage;
    }

    public void setItemSlots(List<Integer> ints) {
        this.itemSlots = ints;
        this.updateInventory();
    }

    @Override
    public void guiFill(ClickItem clickableItem) {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (itemSlots.contains(slot)) continue;
            if (getItem(slot) != null) continue;
            this.setItem(slot, clickableItem);
        }
    }

    public void setItems(List<ClickItem> clickableItems) {
        this.paginationItems = clickableItems;
        this.updateInventory();
    }

    public boolean isLastPage() {
        return this.page == this.getLastPage();
    }

    public boolean isFirstPage() {
        return this.page == 0;
    }

    private boolean updateInventory() {
        int clickableItemSize = this.paginationItems.size();
        int itemSlotSize = this.itemSlots.size();

        int first = this.page * itemSlotSize;
        int last = (this.page + 1) * itemSlotSize;
        if (clickableItemSize <= first) return false;
        if (first < 0) return false;

        int m = 0;
        for (; first < last; first++) {
            ClickItem clickableItem = (clickableItemSize > first) ? this.paginationItems.get(first) : new ClickItem(new Item().material(Material.AIR), inventoryClickEvent -> {});
            this.setItem(this.itemSlots.get(m), clickableItem);
            m++;
        }
        return true;
    }

}
