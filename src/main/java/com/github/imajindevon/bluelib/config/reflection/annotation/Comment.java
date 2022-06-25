package com.github.imajindevon.bluelib.config.reflection.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Comment {
    /**
     * Get the comment of the field.
     *
     * @return the comment of the field
     */
    @NotNull String @NotNull [] value();
}
