package com.github.imajindevon.bluelib.config.reflection;

import com.github.imajindevon.bluelib.annotation.Unsafe;
import com.github.imajindevon.bluelib.chat.ChatUtil;
import com.github.imajindevon.bluelib.config.reflection.annotation.Optional;
import com.github.imajindevon.bluelib.util.naming.KebabCaseConverter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public final class ReflectiveConfigInjector {
    private ReflectiveConfigInjector() {
    }

    /**
     * Inject the configuration values into the given instance.
     * All fields names will by default converted to kebab-case.
     * This can be changed by overriding the {@link ReflectiveConfig#getNamingConventionConverter()} method.
     * <p>
     * If a field is annotated with {@link Optional}, or {@code copyDefaults} is true, and the field is not set,
     * no exception will be thrown and the default value will be used.
     *
     * @param instance         the instance to inject into
     * @param configuration    the class to retrieve the values from
     * @param translateStrings if true, all strings will be color translated
     * @param copyDefaults     if a field is missing, the field's current value will be copied
     * @param <T>              the type of the instance
     * @throws IllegalAccessException   if an unauthorized access is attempted
     * @throws IllegalArgumentException if the config is not compatible with the given instance
     * @see ChatUtil#translate(String)
     * @see KebabCaseConverter#fromCamelCase(String)
     */
    @Unsafe(
        {
            "Uses reflection.",
            "If a SecurityManager is in effect, this is likely to error."
        }
    )
    public static <T extends ReflectiveConfig> void getAndInject(
        @NotNull T instance, @NotNull ConfigurationSection configuration,
        boolean translateStrings, boolean copyDefaults
    ) throws IllegalAccessException {
        Class<?> clazz = instance.getClass();
        Class<?>[] typesToIgnore = ReflectiveConfigUtils.getIgnoredTypes(clazz);

        for (Field field : clazz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();

            if (ReflectiveConfigUtils.shouldIgnoreField(field, typesToIgnore)) {
                continue;
            }

            field.setAccessible(true);

            String path = ReflectiveConfigUtils.extractQualifiedPath(instance, field);
            Object value;

            if (configuration.isSet(path)) {
                value = configuration.get(path);

                if (value == null) {
                    if (fieldType.isPrimitive()) {
                        throw new IllegalArgumentException("Received null for non-null field " + fieldName);
                    }
                } else {
                    Class<?> valueType = value.getClass();

                    if (!fieldType.isAssignableFrom(valueType)) {
                        throw new IllegalArgumentException(
                            "Entry at %s is of type %s, but field %s is of type %s"
                                .formatted(path, valueType, fieldName, fieldType)
                        );
                    }
                }
            } else {
                value = field.get(instance);

                if (copyDefaults) {
                    configuration.set(path, value);
                } else if (!field.isAnnotationPresent(Optional.class)) {
                    throw new IllegalArgumentException("Field " + path + " is not present in the configuration, nor " +
                                                           "is it optional");
                }
            }

            if (translateStrings) {
                value = attemptStringTranslations(value);
            }
            injectValue(field, instance, value);
        }
    }

    /**
     * This function assumes that the given field is accessible.
     *
     * @param field    the field to inject the value into
     * @param instance the instance holding the field
     * @param value    the value to inject
     * @param <T>      the type of the instance
     */
    static <T> void injectValue(
        @NotNull Field field, @NotNull T instance,
        @Nullable Object value
    ) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException exception) {
            // Since we are forcefully allowing access to the field, this should never happen.
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("ChainOfInstanceofChecks")
    @Nullable
    @Contract("null -> null")
    private static <T> Object attemptStringTranslations(@Nullable T value) {
        if (value instanceof String str) {
            return ChatUtil.translate(str);
        }
        if (value instanceof List<?> list) {
            if (!list.isEmpty() && list.get(0) instanceof String) {
                return ChatUtil.translateAllNullable((Collection<String>) list);
            }
        }
        return value;
    }
}
