package com.eml.hstfll.features.booking.application.interfaces.usecases.dtos;

import lombok.Data;

import java.util.Date;

public class DeleteBookingUseCaseDTO {

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
