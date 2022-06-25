package com.github.imajindevon.bluelib.item;

import com.github.imajindevon.bluelib.chat.ChatUtil;
import com.github.imajindevon.bluelib.chat.annotation.FutureColored;
import com.github.imajindevon.bluelib.item.pdc.PdcEntry;
import com.github.imajindevon.bluelib.item.pdc.PdcUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemBuilder {
    private @Nullable String displayName = null;
    private @Nullable List<String> lore = null;
    private @Nullable Map<Enchantment, Integer> enchantments = null;
    private @Nullable Set<PdcEntry<?, ?>> pdcEntries;

    private Material material;

    private int amount = 1;

    /**
     * Creates a new ItemBuilder with the given material.
     *
     * @param material the material of the item
     */
    public ItemBuilder(@NotNull Material material) {
        this.material = material;
    }

    /**
     * Copies an {@link ItemStack} into this builder.
     * This does copies everything but the NBT data (persistent data is considered NBT data).
     *
     * @param item the item
     * @return this
     */
    @Contract("_ -> new")
    public static ItemBuilder safeCopy(@NotNull ItemStack item) {
        ItemBuilder builder = new ItemBuilder(item.getType());

        builder.addEnchantments(item.getEnchantments())
               .setAmount(item.getAmount());

        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();

            return builder.setDisplayName(meta.getDisplayName())
                          .setLore(meta.getLore());
        }
        return builder;
    }

    /**
     * Set the display name of the item.
     *
     * @param displayName the display name of the item
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Set the lore of the item.
     * To remove the lore, use {@link #removeLore}.
     *
     * @param lore the lore of the item, or null to remove it
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setLore(@Nullable List<@Nullable String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Add a map of enchantments to the item.
     * If {@code enchantments} is null, no enchantments will be added.
     *
     * @param enchantments the enchantments to add, or null
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder addEnchantments(@Nullable Map<Enchantment, Integer> enchantments) {
        if (enchantments == null) {
            return this;
        }
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Set the amount of the item.
     *
     * @param amount the new amount
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Tag this item by adding byte with a value of 1.
     * This same effect can be achieved through the {@link PdcUtil#tryTagItem} method.
     *
     * @param key the key of the persistent data
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder tag(@NotNull NamespacedKey key) {
        if (this.pdcEntries == null) {
            this.pdcEntries = new HashSet<>(1);
        }
        this.pdcEntries.add(new PdcEntry<>(key, PersistentDataType.BYTE, (byte) 1));
        return this;
    }

    /**
     * Set the lore of the item, while translating the provided list.
     * To remove the lore, instead of passing null into this method, use {@link #removeLore} to be more specific.
     *
     * @param lore the lore of the item, or null to remove it
     * @return this
     * @see ChatUtil#translateAllNullable
     */
    @Contract("_ -> this")
    public ItemBuilder setTranslatedLore(@Nullable List<@Nullable @FutureColored String> lore) {
        this.lore = ChatUtil.translateAllNullable(lore);
        return this;
    }

    /**
     * Set the display name of the item.
     * The display name will be translated.
     *
     * @param displayName the display name of the item, or null
     * @return this
     * @see ChatUtil#translate
     */
    @Contract("_ -> this")
    public ItemBuilder setTranslatedDisplayName(@Nullable @FutureColored String displayName) {
        this.displayName = ChatUtil.translate(displayName);
        return this;
    }

    /**
     * Extends the lore of the item with the given lines.
     * The strings contained in {@code lore}, if null, will be represented as blank lines.
     *
     * @param lore the lines to add
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder extendLore(@Nullable String @NotNull ... lore) {
        for (String line : lore) {
            this.addLore(line);
        }
        return this;
    }

    /**
     * Add a line of lore to the item.
     *
     * @param lore the new line, or null for a blank line
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder addLore(@Nullable String lore) {
        if (this.lore == null) {
            this.lore = new ArrayList<>(3);
        }
        this.lore.add(lore);
        return this;
    }

    /**
     * Set the lore of the item.
     *
     * @param lore the new lore, or null to remove it
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setLore(@Nullable String @Nullable ... lore) {
        if (lore == null) {
            return this.removeLore();
        }
        this.lore = new ArrayList<>(Arrays.asList(lore));
        return this;
    }

    /**
     * Remove the lore of the item.
     *
     * @return this
     */
    @Contract("-> this")
    public ItemBuilder removeLore() {
        this.lore = null;
        return this;
    }

    /**
     * Set the lore of the item.
     * The strings contained in {@code lore}, if null, will be represented as blank lines.
     * The strings will be translated.
     *
     * @param lore the new lore, or null to remove the lore
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setLoreTranslated(@Nullable @FutureColored String @Nullable ... lore) {
        if (lore == null) {
            return this.removeLore();
        }
        this.lore = Arrays.asList(ChatUtil.translateArray(lore));
        return this;
    }

    /**
     * Build the item.
     *
     * @return a new {@link ItemStack}
     */
    @NotNull
    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta meta = item.getItemMeta();

        if (this.enchantments != null) {
            item.addEnchantments(this.enchantments);
        }

        if (meta == null) {
            if (this.usesItemMeta()) {
                throw new UnsupportedOperationException("ItemStack's without an ItemMeta cannot have a display name");
            }
            return item;
        }

        meta.setDisplayName(this.displayName);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * @return if this ItemBuilder's result will use {@link ItemMeta}
     */
    @SuppressWarnings("MethodWithMoreThanThreeNegations")
    private boolean usesItemMeta() {
        return this.displayName != null || this.lore != null || (this.pdcEntries != null && !this.pdcEntries.isEmpty());
    }

    /**
     * Clear all enchantments from the item.
     *
     * @return this
     */
    @Contract("-> this")
    public ItemBuilder clearEnchantments() {
        this.enchantments = null;
        return this;
    }

    /**
     * Get the material of the item.
     *
     * @return the material
     */
    @NotNull
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Set the material of the future item.
     *
     * @param material the material
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setMaterial(@NotNull Material material) {
        this.material = material;
        return this;
    }

    /**
     * Apply persistent data to the item.
     *
     * @param key  the key of this data
     * @param type the type of the data
     * @param data data
     * @param <T>  the primitive that the data is serialized into
     * @param <Z>  the representation of the data
     * @return this
     */
    @Contract("_, _, _ -> this")
    public <T, Z> ItemBuilder applyPersistentData(
        @NotNull NamespacedKey key,
        @NotNull PersistentDataType<T, Z> type,
        @NotNull Z data
    ) {
        if (this.pdcEntries == null) {
            this.pdcEntries = new HashSet<>(1);
        }
        this.pdcEntries.add(new PdcEntry<>(key, type, data));
        return this;
    }

    /**
     * Clear all properties of the item.
     * This also clears the cached result.
     * The material will be retained.
     *
     * @return this
     */
    @Contract("-> this")
    public ItemBuilder clear() {
        this.displayName = null;
        this.lore = null;
        this.enchantments = null;
        this.pdcEntries = null;
        this.amount = 0;
        return this;
    }

    /**
     * Increment the amount of the item by 1.
     *
     * @return this
     */
    @Contract("-> this")
    public ItemBuilder incrementAmount() {
        this.amount++;
        return this;
    }

    /**
     * Increment the amount of the item by the specified amount.
     *
     * @param incrementBy the amount to increment
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder incrementAmount(int incrementBy) {
        this.amount += incrementBy;
        return this;
    }
}
