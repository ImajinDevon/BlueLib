package com.github.imajindevon.bluelib.net;

import org.jetbrains.annotations.Nullable;

/**
 * An exception raised when an API returns an error with a status or error code.
 */
public class ApiStatusException extends RuntimeException {
    private final int status;

    /**
     * Instantiates a new ApiStatusException.
     *
     * @param status  the status or error code returned by the API
     * @param message the error message
     */
    public ApiStatusException(int status, @Nullable String message) {
        super(message);
        this.status = status;
    }

    /**
     * Get the status or error code returned by the API.
     *
     * @return the status
     */
    public int status() {
        return this.status;
    }
}
