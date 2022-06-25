package com.github.imajindevon.bluelib.item;

import com.github.imajindevon.bluelib.chat.annotation.FutureColored;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ItemFactories {
    private ItemFactories() {
    }

    /**
     * Creates a new {@link ItemStack} with the given material and display name.
     *
     * @param material    the material
     * @param displayName the display name
     * @return the item
     */
    @NotNull
    public static ItemStack createItemTranslated(
        @NotNull Material material,
        @NotNull @FutureColored String displayName
    ) {
        return new ItemBuilder(material).setTranslatedDisplayName(displayName)
                                        .build();
    }

    /**
     * Creates a new {@link ItemStack} with the given material, display name, and lore.
     *
     * @param material    the material
     * @param displayName the display name
     * @param lore        the lore
     * @return the item
     */
    @NotNull
    public static ItemStack createItemTranslated(
        @NotNull Material material, @NotNull @FutureColored String displayName,
        @Nullable @FutureColored List<String> lore
    ) {
        return new ItemBuilder(material).setTranslatedDisplayName(displayName)
                                        .setLore(lore)
                                        .build();
    }
}
