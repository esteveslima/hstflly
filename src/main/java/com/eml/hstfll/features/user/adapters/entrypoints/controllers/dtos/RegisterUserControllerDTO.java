package com.eml.hstfll.features.user.adapters.entrypoints.controllers.dtos;

import com.eml.hstfll.features.user.domain.entities.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class RegisterUserControllerDTO {

    public class Request {
        @Data
        public static class Body {
            @NotNull()
            @Size(min = 5, max = 50)
            public String username;

            @NotNull()
            @Size(min = 8, max = 100)
            public String password;

            @NotNull()
            public UserEntity.UserType type;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public int id;
            public String username;
            public UserEntity.UserType type;
        }
    }

}
