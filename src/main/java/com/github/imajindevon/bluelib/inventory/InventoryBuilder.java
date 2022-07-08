package com.github.imajindevon.bluelib.inventory;

import com.github.imajindevon.bluelib.chat.ChatUtil;
import com.github.imajindevon.bluelib.chat.annotation.FutureColored;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @deprecated this class is not finished
 */
@Deprecated(forRemoval = false, since = "0.1.1")
public class InventoryBuilder {
    private final List<ItemStack> contents;
    private Map<Character, ItemStack> keyMap;
    private int rows;
    private @NotNull String @Nullable [] pattern = null;

    @Nullable
    private final String title;

    /**
     * Create a new InventoryBuilder.
     *
     * @param title the title of the inventory, or null for default
     * @param rows  the amount of rows in the inventory
     */
    public InventoryBuilder(@Nullable @FutureColored String title, @Range(from = 1, to = 6) int rows) {
        this.title = ChatUtil.translateNullable(title);
        this.keyMap = new HashMap<>(2);
        this.rows = rows;
        this.contents = new ArrayList<>(rows * 9);
    }

    /**
     * Add a key to the key map.
     *
     * @param c    the char
     * @param item the item
     * @return this
     */
    @Contract("_, _ -> this")
    public InventoryBuilder addKey(char c, @NotNull ItemStack item) {
        this.keyMap.put(c, item);
        return this;
    }

    /**
     * Set the amount of rows in the inventory;
     *
     * @param rows the amount of rows
     * @return this
     */
    @Contract("_ -> this")
    public InventoryBuilder setRows(int rows) {
        if (rows > 6 || rows < 1) {
            throw new IllegalArgumentException("`rows` must be between 1-6, received: " + rows);
        }
        this.rows = rows;
        return this;
    }

    /**
     * Set the key map of this inventory.
     * <p>
     * A key map associates pattern characters to their items.
     *
     * @param keyMap the key map
     * @return this
     */
    @Contract("_ -> this")
    public InventoryBuilder setKeyMap(@NotNull Map<Character, ItemStack> keyMap) {
        this.keyMap = keyMap;
        return this;
    }

    /**
     * Set the item pattern of this inventory.
     *
     * @param pattern the pattern
     * @return this
     */
    @Contract("_ -> this")
    public InventoryBuilder setPattern(@NotNull String @NotNull ... pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * Remove the item pattern of this inventory.
     *
     * @return this
     */
    @Contract("-> this")
    public InventoryBuilder removePattern() {
        this.pattern = null;
        return this;
    }

    // TODO unfinished
}
