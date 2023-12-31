package com.eml.hstflly.features.user.application.exceptions;

public class UnauthorizedException extends RuntimeException {
    public final Object payload;

    public UnauthorizedException() {
        super();
        this.payload = null;
    }
    public UnauthorizedException(Object payload) {
        super();
        this.payload = payload;
    }

    public UnauthorizedException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}