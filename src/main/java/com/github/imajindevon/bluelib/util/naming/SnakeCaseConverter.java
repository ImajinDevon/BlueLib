package com.github.imajindevon.bluelib.util.naming;

import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfig;
import org.jetbrains.annotations.NotNull;

public final class SnakeCaseConverter implements NamingConventionConverter {
    /**
     * A singleton instance of this converter.
     *
     * @see ReflectiveConfig#getNamingConventionConverter()
     */
    public static final SnakeCaseConverter INSTANCE = new SnakeCaseConverter();

    private SnakeCaseConverter() {
    }

    /**
     * Calls {@link #fromCamelCase(String)} with the given name.
     *
     * @param name the name to convert
     * @return the snake case version of the name
     */
    @NotNull
    public static String convertFromCamelCase(@NotNull String name) {
        return INSTANCE.fromCamelCase(name);
    }

    @Override
    @NotNull
    public String fromCamelCase(@NotNull String name) {
        return NameConversions.separateUppercase('_', name);
    }
}
