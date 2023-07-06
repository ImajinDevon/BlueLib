package com.github.imajindevon.bluelib.config.reflection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells the injector and writer to ignore this field.
 *
 * @see IgnoreTypes
 * @see com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigInjector
 * @see com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigWriter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreField {
}
