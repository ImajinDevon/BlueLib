package com.github.imajindevon.bluelib.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SkullBuilder extends ItemBuilder {
    private final String base64;

    /**
     * Create a new SkullBuilder.
     * @param base64 the base64 representation of this skull data
     */
    public SkullBuilder(@NotNull String base64) {
        super(Material.PLAYER_HEAD);
        this.base64 = base64;
    }

    @Override
    @Contract("_ -> fail")
    public ItemBuilder setMaterial(@NotNull Material material) {
        throw new UnsupportedOperationException("Cannot change materials within SkullBuilder.");
    }

    /**
     * Get the base64 representation of this skull data.
     * @return the base64
     */
    @NotNull
    public String getBase64() {
        return this.base64;
    }

    @Override
    @NotNull
    public ItemStack build() {
        ItemStack item = super.build();
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        assert meta != null;

        SkullUtil.setSkinViaBase64(meta, this.base64);

        item.setItemMeta(meta);
        return item;
    }
}
