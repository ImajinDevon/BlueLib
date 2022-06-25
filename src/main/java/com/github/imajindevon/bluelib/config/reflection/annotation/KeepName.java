package com.github.imajindevon.bluelib.config.reflection.annotation;

import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfig;
import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigInjector;
import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigWriter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the injector and writer to use the exact name of this field.
 *
 * @see ReflectiveConfig#getNamingConventionConverter()
 * @see ReflectiveConfigWriter#copyFields(Object, ConfigurationSection)
 * @see ReflectiveConfigInjector#getAndInject(Object, ConfigurationSection, boolean, boolean)
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeepName {
}
