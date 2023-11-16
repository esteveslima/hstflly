package com.eml.hstflly.features.property.application.interfaces.usecases.dtos;

import lombok.Data;

public class DeletePropertyUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int hostUserId;
    }

    @Data
    public static class Result {
        public final int id;
    }

}
