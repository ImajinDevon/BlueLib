package com.github.imajindevon.bluelib.config.reflection;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class ReflectiveConfigWriter {
    private ReflectiveConfigWriter() {
    }

    /**
     * Write the fields of the given instance to the given configuration section.
     *
     * @param instance the instance to retrieve the fields from
     * @param copyTo   the configuration section to copy the values to
     * @param <T>      the type of the instance
     *
     * @throws IllegalAccessException if an unauthorized access is attempted
     * @since 1.0.0
     */
    public static <T extends ReflectiveConfig> void copyFields(
        @NotNull T instance,
        @NotNull ConfigurationSection copyTo
    )
    throws IllegalAccessException
    {
        Class<?> clazz = instance.getClass();
        Class<?>[] typesToIgnore = ReflectiveConfigUtils.getIgnoredTypes(clazz);

        for (Field field : clazz.getDeclaredFields()) {
            if (ReflectiveConfigUtils.shouldIgnoreField(field, typesToIgnore)) {
                continue;
            }

            field.setAccessible(true);

            String path = ReflectiveConfigUtils.extractQualifiedPath(instance, field);
            Object fieldValue = field.get(instance);

            copyTo.set(path, fieldValue);
        }
    }
}
