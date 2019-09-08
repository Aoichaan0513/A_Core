package org.aoichaan0513.a_core.API;

import org.bukkit.inventory.ItemStack;

import java.util.Comparator;

public class ItemComparator implements Comparator<ItemStack> {

    public int compare(ItemStack item1, ItemStack item2) {
        if (item1 == null && item2 != null) {
            return 1;
        } else if (item1 != null && item2 == null) {
            return -1;
        } else if (item1 == null && item2 == null) {
            return 0;
        } else if (item1.getType().ordinal() > item2.getType().ordinal()) {
            return 1;
        } else if (item1.getType().ordinal() < item2.getType().ordinal()) {
            return -1;
        } else if (item1.getType().ordinal() == item2.getType().ordinal()) {
            if (item1.getDurability() > item2.getDurability()) {
                return 1;
            } else if (item1.getDurability() < item2.getDurability()) {
                return -1;
            } else if (item1.getDurability() == item2.getDurability()) {
                return 0;
            }

            if (item1.getAmount() > item2.getAmount()) {
                return 1;
            } else if (item1.getAmount() < item2.getAmount()) {
                return -1;
            }
        }
        return 0;
    }
}
