package com.github.imajindevon.bluelib.config.reflection.annotation;

import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigInjector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that when declared, notifies the {@link ReflectiveConfigInjector} to ignore this field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreField {
}
