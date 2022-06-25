package com.github.imajindevon.bluelib.chat.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that the provided {@link String} (if not-null) was {@link net.md_5.bungee.api.ChatColor} translated.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE_USE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
public @interface Colored {
}
