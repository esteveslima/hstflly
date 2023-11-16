package com.eml.hstflly.features.user.application.interfaces.gateways.database;

import com.eml.hstflly.features.user.domain.entities.UserEntity;
import com.eml.hstflly.features.user.application.exceptions.UserAlreadyExistsException;
import com.eml.hstflly.features.user.application.exceptions.UserNotFoundException;

public interface UserDAO {
    public UserEntity registerUser(UserEntity entity) throws UserAlreadyExistsException;
    public UserEntity getUserById(int id) throws UserNotFoundException;
    public UserEntity getUserByUsername(String username) throws UserNotFoundException;
}
