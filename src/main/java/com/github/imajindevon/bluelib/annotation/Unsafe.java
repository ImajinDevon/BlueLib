package com.github.imajindevon.bluelib.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as using unsafe methods.
 * For example, reflection methods are considered unsafe, and should declare this annotation.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Unsafe {
    /**
     * @return why this method is unsafe
     */
    @NotNull
    String @NotNull [] value();
}
