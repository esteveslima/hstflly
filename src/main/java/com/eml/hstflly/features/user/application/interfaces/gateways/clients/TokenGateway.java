package com.eml.hstflly.features.user.application.interfaces.gateways.clients;

import com.eml.hstflly.features.user.application.exceptions.InvalidTokenException;

public interface TokenGateway<T> {
    public T decodeToken(String token) throws InvalidTokenException;
    public String generateToken(T tokenPayload);
}
