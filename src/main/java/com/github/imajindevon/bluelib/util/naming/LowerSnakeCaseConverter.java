package com.github.imajindevon.bluelib.util.naming;

import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfig;
import org.jetbrains.annotations.NotNull;

/**
 * lower_snake_case {@link NamingConventionConverter} implementation.
 */
public final class LowerSnakeCaseConverter implements NamingConventionConverter {
    /**
     * A singleton instance of this converter.
     *
     * @see ReflectiveConfig#getNamingConventionConverter()
     */
    public static final LowerSnakeCaseConverter INSTANCE = new LowerSnakeCaseConverter();

    private LowerSnakeCaseConverter() {
    }

    /**
     * Convert the given camelCase name to lower_snake_case.
     *
     * @param name the camelCase name to convert
     *
     * @return the name in lower_snake_case
     */
    @Override
    @NotNull
    public String fromCamelCase(@NotNull String name) {
        return NameConversions.separateCamelCase('_', name);
    }
}
