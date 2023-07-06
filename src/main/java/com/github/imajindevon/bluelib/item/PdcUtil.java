package com.github.imajindevon.bluelib.item;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class containing methods for reading and applying persistent data.
 */
public final class PdcUtil {
    private PdcUtil() {
    }

    /**
     * Apply a string to the given {@link PersistentDataHolder}.
     *
     * @param holder the holder
     * @param key    the key of the entry
     * @param string the string
     */
    public static void applyString(
        @NotNull PersistentDataHolder holder, @NotNull NamespacedKey key,
        @NotNull String string
    )
    {
        holder.getPersistentDataContainer().set(key, PersistentDataType.STRING, string);
    }

    /**
     * Get a string from the {@link PersistentDataHolder}.
     *
     * @param holder the holder, or null
     * @param key    the key
     *
     * @return the string, or null if it does not exist or {@code holder} is null
     */
    @Contract("null, _ -> null")
    public static String getString(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        if (holder == null) {
            return null;
        }
        return holder.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    /**
     * A holder is "tagged" if the holder has a {@link PersistentDataType#BYTE} associated with the given key. The value
     * of this byte is irrelevant.
     *
     * @param holder the holder to check
     * @param key    the key of the data
     *
     * @return if the holder is "tagged"
     */
    public static boolean isTagged(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return holder.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    /**
     * Inserts a byte with a value of {@code 1} into the {@link PersistentDataHolder}.
     *
     * @param holder the holder to insert the byte into
     * @param key    the key of the persistent data
     *
     * @see #isTagged(PersistentDataHolder, NamespacedKey)
     */
    public static void tag(@NotNull PersistentDataHolder holder, @NotNull NamespacedKey key) {
        holder.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
    }
}
