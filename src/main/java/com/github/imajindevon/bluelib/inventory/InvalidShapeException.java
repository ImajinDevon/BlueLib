package com.github.imajindevon.bluelib.inventory;

import org.jetbrains.annotations.Nullable;

/**
 * An exception raised when attempting to construct an invalid inventory shape.
 */
public class InvalidShapeException extends RuntimeException {
    /**
     * Instantiates a new InvalidShapeException.
     *
     * @param message the exception message
     */
    public InvalidShapeException(@Nullable String message) {
        super(message);
    }
}
