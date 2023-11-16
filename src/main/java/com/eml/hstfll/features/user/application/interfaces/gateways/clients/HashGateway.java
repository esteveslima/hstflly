package com.eml.hstfll.features.user.application.interfaces.gateways.clients;

public interface HashGateway {
    public String hashValue(String value);
    public boolean compareHash(String value, String hash);
}
