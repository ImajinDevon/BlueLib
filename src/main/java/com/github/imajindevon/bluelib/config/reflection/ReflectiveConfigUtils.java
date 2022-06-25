package com.github.imajindevon.bluelib.config.reflection;

import com.github.imajindevon.bluelib.annotation.Unsafe;
import com.github.imajindevon.bluelib.config.reflection.annotation.IgnoreField;
import com.github.imajindevon.bluelib.config.reflection.annotation.IgnoreTypes;
import com.github.imajindevon.bluelib.config.reflection.annotation.QualifiedPath;
import com.github.imajindevon.bluelib.config.reflection.annotation.SuperPath;
import com.github.imajindevon.bluelib.util.naming.NamingConventionConverter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

final class ReflectiveConfigUtils {
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    private ReflectiveConfigUtils() {
    }

    @Unsafe("Uses reflection.")
    static <T> @NotNull Class<?> @NotNull [] getIgnoredTypes(@NotNull Class<T> clazz) {
        IgnoreTypes ignoreAnnotation = clazz.getAnnotation(IgnoreTypes.class);

        return ignoreAnnotation == null
                   ? EMPTY_CLASS_ARRAY
                   : ignoreAnnotation.value();
    }

    @NotNull
    @Unsafe("Uses reflection.")
    static <T extends ReflectiveConfig> String extractQualifiedPath(@NotNull T config, @NotNull Field field) {
        String fieldName = field.getName();

        NamingConventionConverter converter = config.getNamingConventionConverter();
        String key = converter.fromCamelCase(fieldName);

        SuperPath superPath = field.getDeclaredAnnotation(SuperPath.class);
        QualifiedPath qualifiedPath = field.getDeclaredAnnotation(QualifiedPath.class);

        if (superPath != null) {
            if (qualifiedPath != null) {
                throw new IllegalArgumentException("Field " + fieldName + " cannot declare both @SuperPath and " +
                                                       "@QualifiedPath");
            }
            return superPath.value() + '.' + key;
        }
        if (qualifiedPath == null) {
            return key;
        }
        return qualifiedPath.value();
    }

    @Unsafe("Uses reflection.")
    static boolean shouldIgnoreField(@NotNull Field field, @NotNull Class<?> @NotNull [] ignoreTypes) {
        if (field.isAnnotationPresent(IgnoreField.class)) {
            return true;
        }

        Class<?> fieldType = field.getType();

        for (Class<?> ignoreType : ignoreTypes) {
            if (ignoreType.isAssignableFrom(fieldType)) {
                return true;
            }
        }
        return false;
    }
}
