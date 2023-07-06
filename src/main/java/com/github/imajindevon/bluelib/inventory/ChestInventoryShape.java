package com.github.imajindevon.bluelib.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ChestInventoryShape {
    private final String[] shape;

    /**
     * Instantiates a new ChestInventoryShape.
     *
     * @param shape the inventory shape matrix
     *
     * @throws InvalidShapeException if the matrix is empty, has a length greater than 6, or any row in the matrix does
     *                               not have a length of 9
     */
    public ChestInventoryShape(@NotNull String @NotNull ... shape) {
        if (shape.length == 0 || shape.length > 6) {
            throw new InvalidShapeException("Chest inventories may only be of lengths 1-6; Has length " + shape.length);
        }
        for (String row : shape) {
            if (row.length() != 9) {
                throw new InvalidShapeException("Rows of chest inventories may only be 9 slots wide.");
            }
        }
        this.shape = shape.clone();
    }

    /**
     * Apply this inventory shape to the given inventory.<br> All whitespace characters are considered empty slots.
     *
     * @param itemMap   the character-item map
     * @param inventory the inventory to be modified
     */
    public void applyShape(@NotNull Map<Character, ItemStack> itemMap, @NotNull Inventory inventory) {
        for (int rowIndex = 0; rowIndex < this.shape.length; rowIndex++) {
            String row = this.shape[rowIndex];

            for (int col = 0; col < 9; col++) {
                char ch = row.charAt(col);

                if (!Character.isWhitespace(ch)) {
                    inventory.setItem(rowIndex * 9 + col, itemMap.get(ch));
                }
            }
        }
    }

    /**
     * Get an unmodifiable list containing this shape's inner matrix.
     *
     * @return the new list
     */
    @Contract("-> new")
    public List<String> shape() {
        return List.of(this.shape);
    }
}
