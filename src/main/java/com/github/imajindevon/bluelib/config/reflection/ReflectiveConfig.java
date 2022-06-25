package com.github.imajindevon.bluelib.config.reflection;

import com.github.imajindevon.bluelib.util.naming.KebabCaseConverter;
import com.github.imajindevon.bluelib.util.naming.NamingConventionConverter;
import org.jetbrains.annotations.NotNull;

/**
 * A configuration object that uses reflection to read and write values.
 *
 * @author ImajinDevon
 * @see ReflectiveConfigInjector
 * @see ReflectiveConfigWriter
 * @since 1.0.0
 */
public interface ReflectiveConfig {
    /**
     * Get the naming convention converter to convert the names of the fields to.
     *
     * @return the converter
     */
    @NotNull
    default NamingConventionConverter getNamingConventionConverter() {
        return KebabCaseConverter.INSTANCE;
    }
}
