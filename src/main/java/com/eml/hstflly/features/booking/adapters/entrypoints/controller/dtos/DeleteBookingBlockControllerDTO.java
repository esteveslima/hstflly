package com.eml.hstflly.features.booking.adapters.entrypoints.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DeleteBookingBlockControllerDTO {
    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
