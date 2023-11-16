package com.eml.hstflly.features.booking.application.interfaces.usecases.dtos;

import lombok.Data;

public class CancelBookingUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int guestUserId;
    }

    @Data
    public static class Result {
        public final int id;
    }

}
