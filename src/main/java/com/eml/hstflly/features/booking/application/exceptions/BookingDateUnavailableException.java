package com.eml.hstflly.features.booking.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class BookingDateUnavailableException extends RuntimeException {
    public final ExceptionPayload payload;

//    public BookingDateUnavailableException() {
//        super();
//        this.payload = null;
//    }
    public BookingDateUnavailableException(ExceptionPayload payload) {
        super();
        this.payload = payload;
    }

    public BookingDateUnavailableException(ExceptionPayload payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionPayload {
        public final Date checkinDate;
        public final Date checkoutDate;
    }

}
