package com.github.imajindevon.bluelib.chat;

import com.github.imajindevon.bluelib.chat.annotation.FutureColored;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A logger that sends messages to the console sender. This allows colored messages to be logged to the console.
 *
 * @see ConsoleCommandSender
 */
public class CsLogger {
    private final String rawPrefix;

    /**
     * Instantiates a new CsLogger. The prefix will be the plugins name, surrounded by square brackets and a trailing
     * whitespace.
     * <br>
     * Example: {@code [BlueLib] <MESSAGE>}
     *
     * @param plugin the plugin using this logger
     */
    public CsLogger(@NotNull Plugin plugin) {
        this("[" + plugin.getName() + "] ");
    }

    /**
     * Instantiates a new CsLogger.
     *
     * @param rawPrefix the prefix to use for this logger
     */
    public CsLogger(@NotNull String rawPrefix) {
        this.rawPrefix = rawPrefix;
    }

    /**
     * Log the given message, or just the prefix if the message is null.
     *
     * @param message the message
     */
    public void log(@Nullable String message) {
        if (message == null) {
            Bukkit.getConsoleSender().sendMessage(this.rawPrefix);
            return;
        }
        Bukkit.getConsoleSender().sendMessage(this.rawPrefix + message);
    }

    /**
     * Translate and log the given message, or just the prefix if the message is null.
     *
     * @param message message
     */
    public void logTranslated(@FutureColored @Nullable String message) {
        this.log(ChatUtil.translateNullable(message));
    }

    /**
     * Get this logger's prefix.
     *
     * @return the prefix
     */
    @NotNull
    public String rawPrefix() {
        return this.rawPrefix;
    }
}
