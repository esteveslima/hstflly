package com.eml.hstfll.features.property.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PropertyNotFoundRuntimeException extends RuntimeException {
    public final ExceptionPayload payload;

    public PropertyNotFoundRuntimeException() {
        super();
        this.payload = null;
    }
    public PropertyNotFoundRuntimeException(ExceptionPayload payload) {
        super();
        this.payload = payload;
    }

    public PropertyNotFoundRuntimeException(ExceptionPayload payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionPayload {
        public final int id;
    }
}
