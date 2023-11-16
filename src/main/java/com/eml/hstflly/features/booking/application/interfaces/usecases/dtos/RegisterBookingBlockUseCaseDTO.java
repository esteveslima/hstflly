package com.eml.hstflly.features.booking.application.interfaces.usecases.dtos;

import lombok.Data;

import java.util.Date;

public class RegisterBookingBlockUseCaseDTO {

    @Data
    public static class Params {
        public final int propertyId;
        public final int hostUserId;
        public final Payload payload;

        @Data
        public static class Payload {
            public final Date checkinDate;
            public final Date checkoutDate;
        }
    }

    @Data
    public static class Result {
        public final int id;
    }

}
