package com.eml.hstfll.features.property.adapters.entrypoints.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Date;

public class GetPropertyControllerDTO {

    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public final String name;
            public final String location;
            public final List<BookingsDataResponse> bookings;

            @Data
            @AllArgsConstructor
            public static class BookingsDataResponse {
                public final Date startDate;
                public final Date endDate;
            }
        }
    }
}
