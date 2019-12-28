package org.aoichaan0513.a_core.Inventorys;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuInventoryHolder implements InventoryHolder {

    private int size;

    public MenuInventoryHolder(int size) {
        this.size = size;
    }

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(null, size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
