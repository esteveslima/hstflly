package com.eml.hstflly.features.user.application.interfaces.usecases.dtos;

import com.eml.hstflly.features.user.domain.entities.UserEntity;
import lombok.Data;

public class RegisterUserUseCaseDTO {
    @Data
    public static class Params {
        public final String username;
        public final String password;
        public final UserEntity.UserType type;
    }

    @Data
    public static class Result {
        public final int id;
        public final String username;
        public final UserEntity.UserType type;
    }
}
