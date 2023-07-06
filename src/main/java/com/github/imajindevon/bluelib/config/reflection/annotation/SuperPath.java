package com.github.imajindevon.bluelib.config.reflection.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation declaring the path up until this field. (e.g) if this field is "a.b.c", the SuperPath would be "a.b". When
 * combined with the {@link QualifiedPath} annotation, {@link IllegalArgumentException} is thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SuperPath {
    /**
     * @return the path up until this field
     */
    @NotNull
    String value();
}
