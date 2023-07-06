package com.github.imajindevon.bluelib.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * A builder class for creating {@link ItemStack}.
 */
@SuppressWarnings("UnusedReturnValue")
public class ItemBuilder {
    private int amount = 1;
    private @Nullable Integer customModelData;
    private @Nullable String displayName;
    private @Nullable Map<Enchantment, Integer> enchantments;
    private @Nullable Set<ItemFlag> itemFlags;
    private @Nullable List<String> lore;
    private Material material;

    /**
     * Creates a new ItemBuilder with the given material.
     *
     * @param material the material of the item
     */
    public ItemBuilder(@NotNull Material material) {
        this.material = material;
    }

    /**
     * Add an enchantment to this item.
     *
     * @param enchantment the enchantment
     * @param level       the enchantment level
     *
     * @return this
     *
     * @see #addEnchantments(Map)
     */
    @Contract("_, _ -> this")
    public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<>(7);
        }
        this.enchantments.put(enchantment, level);
        return this;
    }

    /**
     * Add a map of enchantments to this item.
     *
     * @param enchantments the enchantments to add
     *
     * @return this
     *
     * @see #addEnchantment(Enchantment, int)
     */
    @Contract("_ -> this")
    public ItemBuilder addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            this.addEnchantment(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Add a line of lore to the item.
     *
     * @param lore the new line, or null for a blank line
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder addLore(@Nullable String lore) {
        if (this.lore == null) {
            this.lore = new ArrayList<>(10);
        }
        this.lore.add(lore == null ? "" : lore);
        return this;
    }

    /**
     * Get this item's amount
     *
     * @return the amount
     */
    public int amount() {
        return this.amount;
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

        if (meta == null) {
            if (this.usesItemMeta()) {
                throw new IllegalArgumentException("Material " + this.material.name() + " does not support ItemMeta.");
            }
            return item;
        }

        if (this.enchantments != null) {
            item.addEnchantments(this.enchantments);
        }

        meta.setDisplayName(this.displayName);
        meta.setCustomModelData(this.customModelData);
        meta.setLore(this.lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Get this item's custom model data.
     *
     * @return the custom model data
     */
    @Nullable
    public Integer customModelData() {
        return this.customModelData;
    }

    /**
     * Get the display name of this item.
     *
     * @return the display name
     */
    @Nullable
    public String displayName() {
        return this.displayName;
    }

    /**
     * Extends the lore of the item with the given lines. The strings contained in {@code lore}, if null, will be
     * represented as blank lines.
     *
     * @param lore the lines to add
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder extendLore(@Nullable String @NotNull ... lore) {
        if (this.lore == null) {
            this.lore = new ArrayList<>(Arrays.asList(lore));
        } else {
            this.lore.addAll(Arrays.asList(lore));
        }
        return this;
    }

    /**
     * Increment the amount of the item by one.
     *
     * @return this
     *
     * @see #incrementAmount(int)
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
     *
     * @return this
     *
     * @see #incrementAmount()
     */
    @Contract("_ -> this")
    public ItemBuilder incrementAmount(int incrementBy) {
        this.amount += incrementBy;
        return this;
    }

    /**
     * Get an unmodifiable copy of this item's flags.
     *
     * @return the item flags
     */
    @NotNull
    @Unmodifiable
    public Set<ItemFlag> itemFlags() {
        return this.itemFlags == null ? Collections.emptySet() : ImmutableSet.copyOf(this.itemFlags);
    }

    /**
     * Get an immutable copy of this item's lore. If this item's lore is {@code null}, an empty immutable list is
     * returned.
     *
     * @return the lore
     *
     * @see ImmutableList
     */
    @NotNull
    @Unmodifiable
    public List<String> lore() {
        return this.lore == null ? Collections.emptyList() : ImmutableList.copyOf(this.lore);
    }

    /**
     * Get this item's material.
     *
     * @return the material
     */
    @NotNull
    public Material material() {
        return this.material;
    }

    /**
     * Set the amount of the item.
     *
     * @param amount the new amount
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Set the custom model data of this item.
     *
     * @param customModelData the custom model data, or null to remove it
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setCustomModelData(@Nullable Integer customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    /**
     * Set the display name of the item.
     *
     * @param displayName the display name of the item
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Set this item's flags.
     *
     * @param itemFlags the item flags
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setItemFlags(@NotNull ItemFlag @NotNull ... itemFlags) {
        this.itemFlags = Set.of(itemFlags);
        return this;
    }

    /**
     * Set the lore of the item.
     *
     * @param lore the new lore, or null to remove it
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setLore(@Nullable List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Set the material of the future item.
     *
     * @param material the material
     *
     * @return this
     */
    @Contract("_ -> this")
    public ItemBuilder setMaterial(@NotNull Material material) {
        this.material = material;
        return this;
    }

    /**
     * Get if this item uses item meta.
     *
     * @return if this ItemBuilder's result will use {@link ItemMeta}
     */
    public boolean usesItemMeta() {
        return this.customModelData != null
            || this.displayName != null
            || !(this.itemFlags == null || this.itemFlags.isEmpty())
            || !(this.lore == null || this.lore.isEmpty());
    }

    /**
     * Copies an {@link ItemStack} into a new builder. Item material, amount, display name, lore, enchantments, and
     * custom model data is copied.
     *
     * @param item the item
     *
     * @return this
     */
    @Contract("_ -> new")
    public static ItemBuilder safeCopy(@NotNull ItemStack item) {
        ItemBuilder builder = new ItemBuilder(item.getType())
            .addEnchantments(item.getEnchantments())
            .setAmount(item.getAmount());

        if (item.getItemMeta() == null) return builder;

        ItemMeta meta = item.getItemMeta();

        if (meta.hasCustomModelData()) {
            builder.setCustomModelData(meta.getCustomModelData());
        }
        return builder
            .setDisplayName(meta.getDisplayName())
            .setLore(meta.getLore())
            .setItemFlags(meta.getItemFlags().toArray(ItemFlag[]::new));
    }
}
