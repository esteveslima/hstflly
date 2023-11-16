package com.eml.hstfll.features.user.application.interfaces;


import com.eml.hstfll.features.user.domain.entities.UserEntity;

public class AuthTokenPayloadDTO {
    public int userId;
    public UserEntity.UserType type;


    public AuthTokenPayloadDTO() {
    }

    public AuthTokenPayloadDTO(int userId, UserEntity.UserType type) {
        this.userId = userId;
        this.type = type;
    }
}
