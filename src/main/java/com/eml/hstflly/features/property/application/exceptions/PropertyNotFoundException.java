package com.eml.hstflly.features.property.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PropertyNotFoundException extends RuntimeException {
    public final ExceptionPayload payload;

    public PropertyNotFoundException() {
        super();
        this.payload = null;
    }
    public PropertyNotFoundException(ExceptionPayload payload) {
        super();
        this.payload = payload;
    }

    public PropertyNotFoundException(ExceptionPayload payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionPayload {
        public final int id;
    }
}
