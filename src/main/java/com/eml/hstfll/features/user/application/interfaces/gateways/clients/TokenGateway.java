package com.eml.hstfll.features.user.application.interfaces.gateways.clients;

import com.eml.hstfll.features.user.application.exceptions.InvalidTokenException;

public interface TokenGateway<T> {
    public T decodeToken(String token) throws InvalidTokenException;
    public String generateToken(T tokenPayload);
}
