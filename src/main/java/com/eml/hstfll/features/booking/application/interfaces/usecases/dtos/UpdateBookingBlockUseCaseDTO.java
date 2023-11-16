package com.eml.hstfll.features.booking.application.interfaces.usecases.dtos;

import lombok.Data;

import java.util.Date;

public class UpdateBookingBlockUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
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
