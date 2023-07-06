package com.github.imajindevon.bluelib.util.naming;

import org.jetbrains.annotations.NotNull;

public interface NamingConventionConverter {
    /**
     * Convert a camelCase name with the conversion method of this convention.
     *
     * @param name the camelCase name to convert
     *
     * @return the converted name
     */
    @NotNull
    String fromCamelCase(@NotNull String name);
}
