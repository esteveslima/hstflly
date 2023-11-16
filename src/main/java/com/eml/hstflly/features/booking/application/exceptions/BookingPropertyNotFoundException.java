package com.eml.hstflly.features.booking.application.exceptions;

public class BookingPropertyNotFoundException extends RuntimeException {
    public final Object payload;

    public BookingPropertyNotFoundException() {
        super();
        this.payload = null;
    }
    public BookingPropertyNotFoundException(Object payload) {
        super();
        this.payload = payload;
    }

    public BookingPropertyNotFoundException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}
