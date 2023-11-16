package com.eml.hstfll.features.booking.application.interfaces.usecases.dtos;

import lombok.Data;

import java.util.Date;

public class UpdateBookingUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int guestUserId;
        public final Payload payload;

        @Data
        public static class Payload {
            public final Integer guestsNumber;
            public final Integer arrivalHour;
            public final Date checkinDate;
            public final Date checkoutDate;
        }
    }

    @Data
    public static class Result {
        public final int id;
    }

}
