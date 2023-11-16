package com.eml.hstfll.features.booking.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BookingNotFoundException extends RuntimeException {
    public final Object payload;

    public BookingNotFoundException() {
        super();
        this.payload = null;
    }
    public BookingNotFoundException(Object payload) {
        super();
        this.payload = payload;
    }

    public BookingNotFoundException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}
