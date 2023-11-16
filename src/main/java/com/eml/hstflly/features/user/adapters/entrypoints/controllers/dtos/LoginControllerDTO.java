package com.eml.hstflly.features.user.adapters.entrypoints.controllers.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class LoginControllerDTO {

    public class Request {
        @Data
        public static class Body {
            @NotNull()
            @Size(min = 5, max = 50)
            public String username;

            @NotNull()
            @Size(min = 8, max = 100)
            public String password;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public String token;
        }
    }

}
