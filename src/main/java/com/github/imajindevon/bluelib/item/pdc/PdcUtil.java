package com.github.imajindevon.bluelib.item.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PdcUtil {
    private PdcUtil() {
    }

    /**
     * Inserts a byte with a value of {@code 1} into the item's {@link org.bukkit.persistence.PersistentDataContainer}.
     *
     * @param item the item to insert the byte into
     * @param key  the key of the persistent data
     * @return if the insertion was successful
     * @see #isTagged(ItemStack, NamespacedKey)
     */
    public static boolean tryTagItem(@NotNull ItemStack item, @NotNull NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return true;
    }

    /**
     * An item is "tagged" if the item has a {@link PersistentDataType#BYTE} associated with the given key.
     * The value of this byte is irrelevant.
     *
     * @param item the item to check
     * @param key  the key of the data
     * @return if the item is "tagged"
     */
    public static boolean isTagged(@NotNull ItemStack item, @NotNull NamespacedKey key) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    /**
     * Get a string from the {@link PersistentDataHolder}.
     *
     * @param holder the holder, or null
     * @param key    the key
     * @return the string, or null if it does not exist or {@code holder} is null
     */
    @Contract("null, _ -> null")
    public static String getString(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        if (holder == null) {
            return null;
        }
        return holder.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }
}
