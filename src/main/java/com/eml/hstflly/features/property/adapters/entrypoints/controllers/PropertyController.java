package com.eml.hstflly.features.property.adapters.entrypoints.controllers;

import com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos.DeletePropertyControllerDTO;
import com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos.GetPropertyControllerDTO;
import com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos.RegisterPropertyControllerDTO;
import com.eml.hstflly.features.property.adapters.entrypoints.controllers.dtos.UpdatePropertyControllerDTO;
import com.eml.hstflly.features.property.application.exceptions.PropertyNotFoundException;
import com.eml.hstflly.features.property.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.DeletePropertyUseCaseDTO;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.GetPropertyUseCaseDTO;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.RegisterPropertyUseCaseDTO;
import com.eml.hstflly.features.property.application.interfaces.usecases.dtos.UpdatePropertyUseCaseDTO;
import com.eml.hstflly.features.user.application.interfaces.AuthTokenPayloadDTO;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/properties")
@Validated
public class PropertyController {

    @Autowired
    @Qualifier("registerPropertyUseCase")
    private UseCase<RegisterPropertyUseCaseDTO.Params, RegisterPropertyUseCaseDTO.Result> registerPropertyUseCase;
    @Autowired
    @Qualifier("getPropertyUseCase")
    private UseCase<GetPropertyUseCaseDTO.Params, GetPropertyUseCaseDTO.Result> getPropertyUseCase;
    @Autowired
    @Qualifier("updatePropertyUseCase")
    private UseCase<UpdatePropertyUseCaseDTO.Params, UpdatePropertyUseCaseDTO.Result> updatePropertyUseCase;
    @Autowired
    @Qualifier("deletePropertyUseCase")
    private UseCase<DeletePropertyUseCaseDTO.Params, DeletePropertyUseCaseDTO.Result> deletePropertyUseCase;

    //

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public RegisterPropertyControllerDTO.Response.Body registerProperty(
            Authentication authentication,
            @Valid @RequestBody RegisterPropertyControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RegisterPropertyUseCaseDTO.Params useCaseParamsDTO = new RegisterPropertyUseCaseDTO.Params(
                    authTokenPayload.userId,
                    new RegisterPropertyUseCaseDTO.Params.Payload(
                            requestBody.name,
                            requestBody.location
                    )
            );

            RegisterPropertyUseCaseDTO.Result useCaseResultDTO = registerPropertyUseCase.execute(useCaseParamsDTO);

            return new RegisterPropertyControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST, UserEntity.UserType.MapValue.HOST})
    public GetPropertyControllerDTO.Response.Body getProperty(
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            GetPropertyUseCaseDTO.Params useCaseParamsDTO = new GetPropertyUseCaseDTO.Params(
                    id
            );

            GetPropertyUseCaseDTO.Result useCaseResultDTO = getPropertyUseCase.execute(useCaseParamsDTO);

            return new GetPropertyControllerDTO.Response.Body(
                    useCaseResultDTO.name,
                    useCaseResultDTO.location
            );
        } catch (PropertyNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Property with id %d not found", exception.payload.id));
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public UpdatePropertyControllerDTO.Response.Body updateProperty(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id,
            @Valid @RequestBody UpdatePropertyControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            UpdatePropertyUseCaseDTO.Params useCaseParamsDTO = new UpdatePropertyUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId,
                    new UpdatePropertyUseCaseDTO.Params.Payload(
                            requestBody.name,
                            requestBody.location
                    )
            );

            UpdatePropertyUseCaseDTO.Result useCaseResultDTO = updatePropertyUseCase.execute(useCaseParamsDTO);

            return new UpdatePropertyControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (PropertyNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property Not found");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public DeletePropertyControllerDTO.Response.Body deleteProperty(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            DeletePropertyUseCaseDTO.Params useCaseParamsDTO = new DeletePropertyUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            DeletePropertyUseCaseDTO.Result useCaseResultDTO = deletePropertyUseCase.execute(useCaseParamsDTO);

            return new DeletePropertyControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (PropertyNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property Not found");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
