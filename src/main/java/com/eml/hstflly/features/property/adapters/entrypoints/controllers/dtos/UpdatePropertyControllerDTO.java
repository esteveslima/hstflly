package com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class UpdatePropertyControllerDTO {
    public static class Request {
        @Data
        public static class Body {
            @NotNull()
            @Size(min = 10, max = 100)
            public String name;

            @NotNull()
            @Size(min = 10, max = 250)
            public String location;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
        }
    }
}
