package com.github.imajindevon.bluelib.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public final class SkullUtil {
    private SkullUtil() {
    }

    /**
     * A method used to set the skin of a player skull via a base64 encoded string.
     *
     * @param meta   the skull meta to modify
     * @param base64 the base64 encoded string
     * @author BoBoBalloon on the Spigot website
     */
    public static void setSkinViaBase64(SkullMeta meta, String base64) {
        try {
            Method setProfile = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            setProfile.setAccessible(true);

            GameProfile profile = new GameProfile(UUID.randomUUID(), "skull-texture");
            profile.getProperties().put("textures", new Property("textures", base64));

            setProfile.invoke(meta, profile);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }
}
