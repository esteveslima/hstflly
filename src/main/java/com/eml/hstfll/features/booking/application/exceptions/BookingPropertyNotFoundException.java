package com.eml.hstfll.features.booking.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

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
