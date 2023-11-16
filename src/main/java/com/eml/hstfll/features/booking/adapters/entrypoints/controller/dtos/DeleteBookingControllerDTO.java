package com.eml.hstfll.features.booking.adapters.entrypoints.controller.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class DeleteBookingControllerDTO {
    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
