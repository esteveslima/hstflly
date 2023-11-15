package com.eml.hstfll.features.property.adapters.entrypoints.controllers;

import com.eml.hstfll.features.property.adapters.entrypoints.controllers.dtos.DeletePropertyControllerDTO;
import com.eml.hstfll.features.property.adapters.entrypoints.controllers.dtos.GetPropertyControllerDTO;
import com.eml.hstfll.features.property.adapters.entrypoints.controllers.dtos.RegisterPropertyControllerDTO;
import com.eml.hstfll.features.property.adapters.entrypoints.controllers.dtos.UpdatePropertyControllerDTO;
import com.eml.hstfll.features.property.application.interfaces.UseCase;
import com.eml.hstfll.features.property.application.interfaces.usecases.DeletePropertyUseCaseDTO;
import com.eml.hstfll.features.property.application.interfaces.usecases.GetPropertyUseCaseDTO;
import com.eml.hstfll.features.property.application.interfaces.usecases.RegisterPropertyUseCaseDTO;
import com.eml.hstfll.features.property.application.interfaces.usecases.UpdatePropertyUseCaseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
    public RegisterPropertyControllerDTO.Response.Body registerProperty(
            @Valid @RequestBody RegisterPropertyControllerDTO.Request.Body requestBody
    ) {
        try {
            final int mockUserId = 1;
            RegisterPropertyUseCaseDTO.Params useCaseParamsDTO = new RegisterPropertyUseCaseDTO.Params(
                    mockUserId,
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
                    useCaseResultDTO.location,
                    useCaseResultDTO.bookings.stream().map((bookingData) -> new GetPropertyControllerDTO.Response.Body.BookingsDataResponse(bookingData.startDate, bookingData.startDate)).toList()
            );
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UpdatePropertyControllerDTO.Response.Body updateProperty(
            @PathVariable @NotNull @Min(0) int id,
            @Valid @RequestBody UpdatePropertyControllerDTO.Request.Body requestBody
    ) {
        try {
            final int mockUserId = 1;
            UpdatePropertyUseCaseDTO.Params useCaseParamsDTO = new UpdatePropertyUseCaseDTO.Params(
                    id,
                    mockUserId,
                    new UpdatePropertyUseCaseDTO.Params.Payload(
                            requestBody.name,
                            requestBody.location
                    )
            );

            UpdatePropertyUseCaseDTO.Result useCaseResultDTO = updatePropertyUseCase.execute(useCaseParamsDTO);

            return new UpdatePropertyControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DeletePropertyControllerDTO.Response.Body deleteProperty(
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            final int mockUserId = 1;
            DeletePropertyUseCaseDTO.Params useCaseParamsDTO = new DeletePropertyUseCaseDTO.Params(
                    id,
                    mockUserId
            );

            DeletePropertyUseCaseDTO.Result useCaseResultDTO = deletePropertyUseCase.execute(useCaseParamsDTO);

            return new DeletePropertyControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
