package com.eml.hstflly.features.booking.application.exceptions;

public class BookingNotEnabledException extends RuntimeException {
    public final Object payload;

    public BookingNotEnabledException() {
        super();
        this.payload = null;
    }
    public BookingNotEnabledException(Object payload) {
        super();
        this.payload = payload;
    }

    public BookingNotEnabledException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}
