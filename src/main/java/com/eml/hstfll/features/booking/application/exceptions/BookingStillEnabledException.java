package com.eml.hstfll.features.booking.application.exceptions;

public class BookingStillEnabledException extends RuntimeException {
    public final Object payload;

    public BookingStillEnabledException() {
        super();
        this.payload = null;
    }
    public BookingStillEnabledException(Object payload) {
        super();
        this.payload = payload;
    }

    public BookingStillEnabledException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}
