package com.eml.hstfll.features.user.application.usecases;

import com.eml.hstfll.features.user.application.interfaces.gateways.clients.HashGateway;
import com.eml.hstfll.features.user.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.user.domain.entities.UserEntity;
import com.eml.hstfll.features.user.application.interfaces.gateways.database.UserDAO;
import com.eml.hstfll.features.user.application.interfaces.usecases.dtos.RegisterUserUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("registerUserUseCase")
public class RegisterUserUseCase implements UseCase<RegisterUserUseCaseDTO.Params, RegisterUserUseCaseDTO.Result> {

    private UserDAO userDAO;
    private HashGateway hashGateway;

    @Autowired
    public RegisterUserUseCase(
            @Qualifier("userJpaDAO") UserDAO userDAO,
            @Qualifier("hashClientGateway") HashGateway hashGateway
    ) {
        this.userDAO = userDAO;
        this.hashGateway = hashGateway;
    }

    @Transactional
    public RegisterUserUseCaseDTO.Result execute(RegisterUserUseCaseDTO.Params params) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(params.getUsername());
        newUser.setType(params.getType());
        String encodedPassword = this.hashGateway.hashValue(params.getPassword());
        newUser.setEncodedPassword(encodedPassword);

        UserEntity registeredUser = this.userDAO.registerUser(newUser);

        return new RegisterUserUseCaseDTO.Result(registeredUser.getId(), registeredUser.getUsername(), registeredUser.getType());
    }
}
