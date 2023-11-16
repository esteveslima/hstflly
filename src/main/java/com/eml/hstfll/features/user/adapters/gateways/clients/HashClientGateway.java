package com.eml.hstfll.features.user.adapters.gateways.clients;

import com.eml.hstfll.features.user.application.interfaces.gateways.clients.HashGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Qualifier("hashClientGateway")
public class HashClientGateway implements HashGateway {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    @Override
    public String hashValue(String value){
        String encodedValue = bCryptPasswordEncoder.encode(value);

        return encodedValue;
    }

    @Override
    public boolean compareHash(String value, String hash) {
        boolean isValueEqualsHash = bCryptPasswordEncoder.matches(value, hash);

        return isValueEqualsHash;
    }
}
