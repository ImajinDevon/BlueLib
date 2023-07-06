package com.github.imajindevon.bluelib.config;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class containing methods for reading and writing to configurations.
 */
public final class ConfigUtil {
    private ConfigUtil() {
    }

    /**
     * Get an array containing each immediate child {@link ConfigurationSection} of the given parent section.
     *
     * @param section the parent section
     *
     * @return the child sections
     */
    @SuppressWarnings("DataFlowIssue")
    @NotNull
    public static ConfigurationSection @NotNull [] getConfigurationSections(@NotNull ConfigurationSection section) {
        return section
            .getKeys(false)
            .stream()
            .filter(section::isConfigurationSection)
            .map(section::getConfigurationSection)
            .toArray(ConfigurationSection[]::new);
    }
}
