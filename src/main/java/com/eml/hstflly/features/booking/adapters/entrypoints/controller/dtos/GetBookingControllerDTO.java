package com.eml.hstflly.features.booking.adapters.entrypoints.controller.dtos;

import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
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
