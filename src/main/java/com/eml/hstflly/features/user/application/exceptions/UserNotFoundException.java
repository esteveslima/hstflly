package com.eml.hstflly.features.user.application.exceptions;

public class UserNotFoundException extends RuntimeException {
    public final Object payload;

    public UserNotFoundException() {
        super();
        this.payload = null;
    }
    public UserNotFoundException(String payload) {
        super();
        this.payload = payload;
    }

    public UserNotFoundException(String payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}