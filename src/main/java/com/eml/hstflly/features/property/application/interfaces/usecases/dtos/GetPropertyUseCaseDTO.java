package com.eml.hstflly.features.property.application.interfaces.usecases.dtos;

import lombok.Data;

public class GetPropertyUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
    }

    @Data
    public static class Result {
        public final String name;
        public final String location;
//        public final List<BookingsDataResult> bookings;
//
//        @Data
//        public static class BookingsDataResult {
//            public final Date checkinDate;
//            public final Date checkoutDate;
//        }
    }

}
