package com.github.imajindevon.bluelib.item.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * In library usage, this class is used to store persistent data in instances of
 * {@link com.github.imajindevon.bluelib.item.ItemBuilder}.
 *
 * @param <T> the primitive representation of the data
 * @param <Z> the type of the data
 */
public class PdcEntry<T, Z> {
    private final NamespacedKey key;
    private final PersistentDataType<T, Z> type;
    private final Z value;

    /**
     * Create a new PdcEntry.
     *
     * @param key   the key of the entry
     * @param type  the type of the entry
     * @param value the value of the entry
     */
    public PdcEntry(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    /**
     * Get the key of this entry.
     *
     * @return the key
     */
    @NotNull
    public NamespacedKey getKey() {
        return this.key;
    }

    /**
     * Get the {@link PersistentDataType} of this entry.
     *
     * @return the type
     */
    @NotNull
    public PersistentDataType<T, Z> getType() {
        return this.type;
    }

    /**
     * Get the value of this entry.
     *
     * @return the value
     */
    @NotNull
    public Z getValue() {
        return this.value;
    }
}
