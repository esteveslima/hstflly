package com.eml.hstfll.features.property.application.interfaces.usecases;

import lombok.Data;

public class DeletePropertyUseCaseDTO {

    @Data
    public static class Params {
        public final int id;
        public final int userId;
    }

    @Data
    public static class Result {
        public final int id;
    }

}
