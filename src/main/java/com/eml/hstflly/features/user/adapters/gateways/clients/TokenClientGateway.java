package com.eml.hstflly.features.user.adapters.gateways.clients;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eml.hstflly.features.user.application.exceptions.InvalidTokenException;
import com.eml.hstflly.features.user.application.interfaces.AuthTokenPayloadDTO;
import com.eml.hstflly.features.user.application.interfaces.gateways.clients.TokenGateway;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Qualifier("tokenClientGateway")
public class TokenClientGateway implements TokenGateway<AuthTokenPayloadDTO> {


    @Value("${JWT_SECRET}") private String jwtSecret;
    @Value("${JWT_EXPIRES_SECONDS}") private String jwtExpiresSeconds;

    @Override
    public AuthTokenPayloadDTO decodeToken(String token) throws InvalidTokenException {
        try {
            DecodedJWT decodedJWT = JWT
                    .require(Algorithm.HMAC256(this.jwtSecret))
                    .build()
                    .verify(token);

            String tokenStringPayloadPart = decodedJWT.getPayload();
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String stringifiedPayload = new String(decoder.decode(tokenStringPayloadPart));

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Map<String, Object> payloadMap = objectMapper.readValue(stringifiedPayload, Map.class);
            AuthTokenPayloadDTO tokenPayload = objectMapper.convertValue(payloadMap, AuthTokenPayloadDTO.class);

            return tokenPayload;
        } catch(JWTVerificationException exception) {
            throw new InvalidTokenException(token, exception);
        } catch(Exception exception) {
            throw new InvalidTokenException(token, exception);
        }
    }

    @Override
    public String generateToken(AuthTokenPayloadDTO tokenPayload) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, Integer.parseInt(this.jwtExpiresSeconds));
        Date jwtExpireDate = cal.getTime();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mappedPayload = objectMapper.convertValue(tokenPayload, Map.class);

        String token = JWT.create()
                .withPayload(mappedPayload)
                .withExpiresAt(jwtExpireDate)
                .sign(Algorithm.HMAC256(this.jwtSecret));

        return token;
    }
}
