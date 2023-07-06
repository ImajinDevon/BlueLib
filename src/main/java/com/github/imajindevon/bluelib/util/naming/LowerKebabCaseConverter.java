package com.github.imajindevon.bluelib.util.naming;

import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfig;
import org.jetbrains.annotations.NotNull;

/**
 * lower-kebab-case {@link NamingConventionConverter} implementation.
 */
public final class LowerKebabCaseConverter implements NamingConventionConverter {
    /**
     * A singleton instance of this converter.
     *
     * @see ReflectiveConfig#getNamingConventionConverter()
     */
    public static final NamingConventionConverter INSTANCE = new LowerKebabCaseConverter();

    private LowerKebabCaseConverter() {
    }

    /**
     * Convert the given camelCase name to lower-kebab-case.
     *
     * @param name the camelCase name to convert
     *
     * @return the name in lower-kebab-case
     */
    @Override
    @NotNull
    public String fromCamelCase(@NotNull String name) {
        return NameConversions.separateCamelCase('-', name);
    }
}
