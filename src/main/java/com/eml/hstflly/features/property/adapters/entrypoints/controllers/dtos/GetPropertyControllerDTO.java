package com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GetPropertyControllerDTO {

    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public final String name;
            public final String location;
//            public final List<BookingsDataResponse> bookings;
//
//            @Data
//            @AllArgsConstructor
//            public static class BookingsDataResponse {
//                public final Date checkinDate;
//                public final Date checkoutDate;
//            }
        }
    }
}
