package com.github.imajindevon.bluelib.config.reflection.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation declaring the field's full, (qualified), path. When combined with the {@link SuperPath} annotation,
 * {@link IllegalArgumentException} is thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QualifiedPath {
    /**
     * @return the full path to this field, including the field name
     */
    @NotNull
    String value();
}
