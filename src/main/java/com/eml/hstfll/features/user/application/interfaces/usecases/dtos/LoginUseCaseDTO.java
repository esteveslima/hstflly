package com.eml.hstfll.features.user.application.interfaces.usecases.dtos;

import com.eml.hstfll.features.user.domain.entities.UserEntity;
import lombok.Data;

public class LoginUseCaseDTO {
    @Data
    public static class Params {
        public final String username;
        public final String password;
    }

    @Data
    public static class Result {
        public final String token;
    }
}
