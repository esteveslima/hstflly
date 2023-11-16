package com.eml.hstfll.features.booking.adapters.entrypoints.controller.dtos;

import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class GetBookingControllerDTO {
    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public final Integer propertyId;
            public Integer guestsNumber;
            public Integer arrivalHour;
            public Date checkinDate;
            public Date checkoutDate;
            public BookingEntity.BookingStatus status;
        }
    }
}
