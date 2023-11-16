package com.eml.hstfll.features.booking.adapters.entrypoints.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CancelBookingControllerDTO {
    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
