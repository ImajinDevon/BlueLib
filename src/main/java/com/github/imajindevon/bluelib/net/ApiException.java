package com.github.imajindevon.bluelib.net;

import org.jetbrains.annotations.Nullable;

/**
 * An exception raised when an API returns an error.
 */
public class ApiException extends RuntimeException {
    /**
     * Instantiates a new ApiException.
     *
     * @param message the error message
     */
    public ApiException(@Nullable String message) {
        super(message);
    }
}
