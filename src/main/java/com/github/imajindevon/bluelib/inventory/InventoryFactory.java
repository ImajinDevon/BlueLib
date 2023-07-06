package com.github.imajindevon.bluelib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class containing methods for creating complex inventories with ease.
 */
public final class InventoryFactory {
    private InventoryFactory() {
    }

    /**
     * Create an inventory with the given amount of rows and title.
     *
     * @param rows  the amount of rows in the inventory
     * @param title the title of the inventory
     *
     * @return the new inventory
     */
    @Contract("_, _ -> new")
    public static Inventory create(int rows, @NotNull String title) {
        return Bukkit.createInventory(null, rows * 9, title);
    }

    /**
     * Create an inventory with the given amount of rows and title, and fill it with the given item.
     *
     * @param rows  the amount of rows in the inventory
     * @param title the title of the inventory
     * @param item  the item to fill the inventory with
     *
     * @return the new inventory
     */
    @Contract("_, _, _ -> new")
    public static Inventory createAndFill(int rows, @NotNull String title, @Nullable ItemStack item) {
        Inventory inventory = create(rows, title);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, item);
        }
        return inventory;
    }
}
