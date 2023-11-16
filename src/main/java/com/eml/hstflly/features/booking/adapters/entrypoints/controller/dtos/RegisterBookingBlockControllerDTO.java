package com.eml.hstflly.features.booking.adapters.entrypoints.controller.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class RegisterBookingBlockControllerDTO {
    public static class Request {
        @Data
        public static class Body {
            @NotNull()
            @Min(0)
            public Integer propertyId;

            @NotNull()
            @FutureOrPresent
            public Date checkinDate;

            @NotNull()
            @Future
            public Date checkoutDate;

            @AssertTrue(message = "Start date must be before End date")
            public boolean isCheckinDateBeforeCheckoutDate() {
                return checkinDate == null || checkoutDate == null || checkinDate.before(checkoutDate);
            }
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
