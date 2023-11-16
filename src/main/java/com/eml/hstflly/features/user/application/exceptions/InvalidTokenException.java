package com.eml.hstflly.features.user.application.exceptions;

public class InvalidTokenException extends RuntimeException {
    public final String payload;

    public InvalidTokenException() {
        super();
        this.payload = null;
    }
    public InvalidTokenException(String payload) {
        super();
        this.payload = payload;
    }

    public InvalidTokenException(String payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}