package com.github.imajindevon.bluelib.chat;

import com.github.imajindevon.bluelib.chat.annotation.Colored;
import com.github.imajindevon.bluelib.chat.annotation.FutureColored;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ChatUtil {
    private ChatUtil() {
    }

    /**
     * Translate the color codes within the given message.
     * Returns an {@link ArrayList} of the translated strings.
     * The returned strings will never be null, but instead empty.
     * If {@code messages} is not null, the type and mutability of the {@link List} will be unknown.
     *
     * @param messages the messages to translate, or null
     * @return the translated strings, or an empty {@link ArrayList} if the provided message was null
     * @see ChatUtil#translate(String)
     */
    @Colored
    @Contract("null -> new")
    public static List<String> translateAll(@Nullable @FutureColored Collection<@Nullable String> messages) {
        if (messages == null) {
            return new ArrayList<>(0);
        }
        return messages.stream()
                       .map(ChatUtil::translate)
                       .collect(Collectors.toList());
    }

    /**
     * Translate the color codes within the given message.
     *
     * @param message the message to translate, or null
     * @return the translated string, or an empty string if the provided message was null
     */
    @NotNull
    @Colored
    public static String translate(@FutureColored @Nullable String message) {
        if (message == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Translate the color codes within the given message.
     * Returns an {@link ArrayList} of the translated strings.
     * The returned strings may be null.
     *
     * @param messages the messages to translate, or null
     * @return the translated strings, or an empty list if the provided message was null
     */
    @Contract("null -> null")
    @Colored
    public static List<String> translateAllNullable(@Nullable @FutureColored Collection<@Nullable String> messages) {
        if (messages == null) {
            return null;
        }
        return messages.stream()
                       .map(ChatUtil::translateNullable)
                       .collect(Collectors.toList());
    }

    /**
     * Translate the color codes within the given message.
     *
     * @param message the message to translate, or null
     * @return the translated string, or null if the provided message was null
     */
    @Contract("null -> null")
    @Colored
    public static String translateNullable(@Nullable @FutureColored String message) {
        if (message == null) {
            return null;
        }
        return translate(message);
    }

    /**
     * Translate the strings contained in the provided array.
     * The returned array may contain null strings.
     *
     * @param messages the messages to translate, or null
     * @return the translated strings
     */
    @Contract("null -> null")
    @Colored
    public static @Nullable String[] translateArray(@Nullable String @Nullable @FutureColored ... messages) {
        if (messages == null) {
            return null;
        }
        return Arrays.stream(messages)
                     .map(ChatUtil::translateNullable)
                     .toArray(String[]::new);
    }
}
