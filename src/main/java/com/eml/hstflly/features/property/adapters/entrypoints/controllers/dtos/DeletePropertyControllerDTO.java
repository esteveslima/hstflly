package com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DeletePropertyControllerDTO {

    //

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
