package com.eml.hstflly.features.property.application.interfaces.usecases.dtos;

import lombok.Data;

public class UpdatePropertyUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int hostUserId;
        public final Payload payload;

        @Data
        public static class Payload {
            public final String name;
            public final String location;
        }
    }

    @Data
    public static class Result {
        public final int id;
    }

}
