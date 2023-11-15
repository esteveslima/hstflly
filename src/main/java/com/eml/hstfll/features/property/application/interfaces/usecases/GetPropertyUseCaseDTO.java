package com.eml.hstfll.features.property.application.interfaces.usecases;

import lombok.Data;

import java.util.List;
import java.util.Date;

public class GetPropertyUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
    }

    @Data
    public static class Result {
        public final String name;
        public final String location;
        public final List<BookingsDataResult> bookings;

        @Data
        public static class BookingsDataResult {
            public final Date startDate;
            public final Date endDate;
        }
    }

}
