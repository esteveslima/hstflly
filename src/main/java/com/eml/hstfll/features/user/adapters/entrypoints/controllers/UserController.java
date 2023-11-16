package com.eml.hstfll.features.user.adapters.entrypoints.controllers;

import com.eml.hstfll.features.user.adapters.entrypoints.controllers.dtos.LoginControllerDTO;
import com.eml.hstfll.features.user.adapters.entrypoints.controllers.dtos.RegisterUserControllerDTO;
import com.eml.hstfll.features.user.application.exceptions.UnauthorizedException;
import com.eml.hstfll.features.user.application.exceptions.UserAlreadyExistsException;
import com.eml.hstfll.features.user.application.exceptions.UserNotFoundException;
import com.eml.hstfll.features.user.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.user.application.interfaces.usecases.dtos.LoginUseCaseDTO;
import com.eml.hstfll.features.user.application.interfaces.usecases.dtos.RegisterUserUseCaseDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    @Qualifier("registerUserUseCase")
    private UseCase<RegisterUserUseCaseDTO.Params, RegisterUserUseCaseDTO.Result> registerUserUseCase;

    @Autowired
    @Qualifier("loginUseCase")
    private UseCase<LoginUseCaseDTO.Params, LoginUseCaseDTO.Result> loginUseCase;

    //

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PermitAll
    public RegisterUserControllerDTO.Response.Body registerUser(
            @Valid @RequestBody RegisterUserControllerDTO.Request.Body requestBody
    ) {
        try {
            RegisterUserUseCaseDTO.Params useCaseParamsDTO = new RegisterUserUseCaseDTO.Params(
                    requestBody.username,
                    requestBody.password,
                    requestBody.type
            );

            RegisterUserUseCaseDTO.Result useCaseResultDTO = registerUserUseCase.execute(useCaseParamsDTO);

            return new RegisterUserControllerDTO.Response.Body(
                    useCaseResultDTO.id,
                    useCaseResultDTO.username,
                    useCaseResultDTO.type
            );
        } catch (UserAlreadyExistsException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User data already exists");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PermitAll
    public LoginControllerDTO.Response.Body loginUser(
            @Valid @RequestBody LoginControllerDTO.Request.Body requestBody
    ) {
        try{
            LoginUseCaseDTO.Params useCaseParamsDTO = new LoginUseCaseDTO.Params(requestBody.username, requestBody.password);

            LoginUseCaseDTO.Result useCaseResultDTO = loginUseCase.execute(useCaseParamsDTO);

            return new LoginControllerDTO.Response.Body(useCaseResultDTO.token);
        } catch (UserNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (UnauthorizedException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login unauthorized");
        }
    }

}
