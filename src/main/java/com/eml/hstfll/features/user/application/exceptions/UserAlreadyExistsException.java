package com.eml.hstfll.features.user.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public final Object payload;

    public UserAlreadyExistsException() {
        super();
        this.payload = null;
    }
    public UserAlreadyExistsException(Object payload) {
        super();
        this.payload = payload;
    }

    public UserAlreadyExistsException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}