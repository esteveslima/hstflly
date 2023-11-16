package com.eml.hstflly.features.user.application.usecases;

import com.eml.hstflly.features.user.application.exceptions.UnauthorizedException;
import com.eml.hstflly.features.user.application.interfaces.AuthTokenPayloadDTO;
import com.eml.hstflly.features.user.application.interfaces.gateways.clients.HashGateway;
import com.eml.hstflly.features.user.application.interfaces.gateways.clients.TokenGateway;
import com.eml.hstflly.features.user.application.interfaces.gateways.database.UserDAO;
import com.eml.hstflly.features.user.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.user.application.interfaces.usecases.dtos.LoginUseCaseDTO;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("loginUseCase")
public class LoginUseCase implements UseCase<LoginUseCaseDTO.Params, LoginUseCaseDTO.Result> {

    private UserDAO userDAO;
    private HashGateway hashGateway;
    private TokenGateway tokenGateway;

    @Autowired
    public LoginUseCase(
            @Qualifier("userJpaDAO") UserDAO userDAO,
            @Qualifier("hashClientGateway") HashGateway hashGateway,
            @Qualifier("tokenClientGateway") TokenGateway tokenGateway
    ) {
        this.userDAO = userDAO;
        this.hashGateway = hashGateway;
        this.tokenGateway = tokenGateway;
    }

    @Transactional
    public LoginUseCaseDTO.Result execute(LoginUseCaseDTO.Params params) {
        UserEntity user = this.userDAO.getUserByUsername(params.username);

        boolean isInputPasswordCorrect = this.hashGateway.compareHash(params.password, user.getEncodedPassword());
        if(!isInputPasswordCorrect){
            throw new UnauthorizedException(params);
        }

        AuthTokenPayloadDTO authTokenPayload = new AuthTokenPayloadDTO(user.getId(), user.getType());
        String authToken = this.tokenGateway.generateToken(authTokenPayload);

        return new LoginUseCaseDTO.Result(authToken);
    }
}
