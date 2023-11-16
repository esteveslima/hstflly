package com.eml.hstfll.features.booking.application.interfaces.usecases.dtos;

import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import lombok.Data;

import java.util.Date;

public class GetBookingUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int guestOrHostUserId;
    }

    @Data
    public static class Result {
        public final Integer propertyId;
        public final Integer guestsNumber;
        public final Integer arrivalHour;
        public final Date checkinDate;
        public final Date checkoutDate;
        public final BookingEntity.BookingStatus status;
    }

}
