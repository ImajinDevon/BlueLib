package com.github.imajindevon.bluelib.config.reflection.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For only ignoring certain fields at a time, use {@link IgnoreField} instead.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.MODULE)
public @interface IgnoreTypes {
    /**
     * If a value can be assigned to a field's type that is contained in this array, the field will be ignored.
     * For example, this means if a field's type is SuperClass, SubClass may be assigned to it.
     *
     * @return the types to ignore
     */
    @NotNull Class<?> @NotNull [] value() default {};
}
