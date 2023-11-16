package com.eml.hstfll.features.booking.adapters.entrypoints.controller;

import com.eml.hstfll.features.booking.adapters.entrypoints.controller.dtos.*;
import com.eml.hstfll.features.booking.application.exceptions.*;
import com.eml.hstfll.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.booking.application.interfaces.usecases.dtos.*;
import com.eml.hstfll.features.user.application.interfaces.AuthTokenPayloadDTO;
import com.eml.hstfll.features.user.domain.entities.UserEntity;
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
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    @Autowired
    @Qualifier("registerBookingUseCase")
    private UseCase<RegisterBookingUseCaseDTO.Params, RegisterBookingUseCaseDTO.Result> registerBookingUseCase;

    @Autowired
    @Qualifier("updateBookingUseCase")
    private UseCase<UpdateBookingUseCaseDTO.Params, UpdateBookingUseCaseDTO.Result> updateBookingUseCase;

    @Autowired
    @Qualifier("deleteBookingUseCase")
    private UseCase<DeleteBookingUseCaseDTO.Params, DeleteBookingUseCaseDTO.Result> deleteBookingUseCase;

    @Autowired
    @Qualifier("getBookingUseCase")
    private UseCase<GetBookingUseCaseDTO.Params, GetBookingUseCaseDTO.Result> getBookingUseCase;

    @Autowired
    @Qualifier("cancelBookingUseCase")
    private UseCase<CancelBookingUseCaseDTO.Params, CancelBookingUseCaseDTO.Result> cancelBookingUseCase;

    @Autowired
    @Qualifier("rebookBookingUseCase")
    private UseCase<RebookBookingUseCaseDTO.Params, RebookBookingUseCaseDTO.Result> rebookBookingUseCase;

    @Autowired
    @Qualifier("registerBookingBlockUseCase")
    private UseCase<RegisterBookingBlockUseCaseDTO.Params, RegisterBookingBlockUseCaseDTO.Result> registerBookingBlockUseCase;

    @Autowired
    @Qualifier("updateBookingBlockUseCase")
    private UseCase<UpdateBookingBlockUseCaseDTO.Params, UpdateBookingBlockUseCaseDTO.Result> updateBookingBlockUseCase;

    @Autowired
    @Qualifier("deleteBookingBlockUseCase")
    private UseCase<DeleteBookingBlockUseCaseDTO.Params, DeleteBookingBlockUseCaseDTO.Result> deleteBookingBlockUseCase;

    //

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST})
    public RegisterBookingControllerDTO.Response.Body registerBooking(
            Authentication authentication,
            @Valid @RequestBody RegisterBookingControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RegisterBookingUseCaseDTO.Params useCaseParamsDTO = new RegisterBookingUseCaseDTO.Params(
                    requestBody.propertyId,
                    authTokenPayload.userId,
                    new RegisterBookingUseCaseDTO.Params.Payload(
                            requestBody.guestsNumber,
                            requestBody.arrivalHour,
                            requestBody.checkinDate,
                            requestBody.checkoutDate
                    )
            );

            RegisterBookingUseCaseDTO.Result useCaseResultDTO = registerBookingUseCase.execute(useCaseParamsDTO);

            return new RegisterBookingControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingPropertyNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Booking Property not found");
        } catch (BookingDateUnavailableException exception){
            String message = String.format("Booking at this Property is unavailable between the dates %s and %s", exception.payload.checkinDate, exception.payload.checkoutDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST})
    public UpdateBookingControllerDTO.Response.Body updateBooking(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id,
            @Valid @RequestBody UpdateBookingControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            UpdateBookingUseCaseDTO.Params useCaseParamsDTO = new UpdateBookingUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId,
                    new UpdateBookingUseCaseDTO.Params.Payload(
                            requestBody.guestsNumber,
                            requestBody.arrivalHour,
                            requestBody.checkinDate,
                            requestBody.checkoutDate
                    )
            );

            UpdateBookingUseCaseDTO.Result useCaseResultDTO = updateBookingUseCase.execute(useCaseParamsDTO);

            return new UpdateBookingControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (BookingDateUnavailableException exception){
            String message = String.format("Booking at this Property is unavailable between the dates %s and %s", exception.payload.checkinDate, exception.payload.checkoutDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST})
    public DeleteBookingControllerDTO.Response.Body deleteBooking(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            DeleteBookingUseCaseDTO.Params useCaseParamsDTO = new DeleteBookingUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            DeleteBookingUseCaseDTO.Result useCaseResultDTO = deleteBookingUseCase.execute(useCaseParamsDTO);

            return new DeleteBookingControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (BookingStillEnabledException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking must be cancelled before deleted");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST, UserEntity.UserType.MapValue.HOST})
    public GetBookingControllerDTO.Response.Body getBooking(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            GetBookingUseCaseDTO.Params useCaseParamsDTO = new GetBookingUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            GetBookingUseCaseDTO.Result useCaseResultDTO = getBookingUseCase.execute(useCaseParamsDTO);

            return new GetBookingControllerDTO.Response.Body(
                    useCaseResultDTO.propertyId,
                    useCaseResultDTO.guestsNumber,
                    useCaseResultDTO.arrivalHour,
                    useCaseResultDTO.checkinDate,
                    useCaseResultDTO.checkoutDate,
                    useCaseResultDTO.status
            );
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST})
    public CancelBookingControllerDTO.Response.Body cancelBooking(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            CancelBookingUseCaseDTO.Params useCaseParamsDTO = new CancelBookingUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            CancelBookingUseCaseDTO.Result useCaseResultDTO = cancelBookingUseCase.execute(useCaseParamsDTO);

            return new CancelBookingControllerDTO.Response.Body(useCaseResultDTO.id);
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (BookingNotEnabledException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking already cancelled");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/{id}/rebook")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.GUEST})
    public RebookBookingControllerDTO.Response.Body rebookBooking(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RebookBookingUseCaseDTO.Params useCaseParamsDTO = new RebookBookingUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            RebookBookingUseCaseDTO.Result useCaseResultDTO = rebookBookingUseCase.execute(useCaseParamsDTO);

            return new RebookBookingControllerDTO.Response.Body(useCaseResultDTO.id);
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (BookingStillEnabledException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking is not cancelled");
        } catch (BookingDateUnavailableException exception){
            String message = String.format("Booking at this Property is unavailable between the dates %s and %s. You can delete this cancelled booking and schedule a new one", exception.payload.checkinDate, exception.payload.checkoutDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    //

    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public RegisterBookingBlockControllerDTO.Response.Body registerBookingBlock(
            Authentication authentication,
            @Valid @RequestBody RegisterBookingBlockControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            RegisterBookingBlockUseCaseDTO.Params useCaseParamsDTO = new RegisterBookingBlockUseCaseDTO.Params(
                    requestBody.propertyId,
                    authTokenPayload.userId,
                    new RegisterBookingBlockUseCaseDTO.Params.Payload(
                            requestBody.checkinDate,
                            requestBody.checkoutDate
                    )
            );

            RegisterBookingBlockUseCaseDTO.Result useCaseResultDTO = registerBookingBlockUseCase.execute(useCaseParamsDTO);

            return new RegisterBookingBlockControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingDateUnavailableException exception){
            String message = String.format("A Booking already exists at this Property between the dates %s and %s", exception.payload.checkinDate, exception.payload.checkoutDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PatchMapping("/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public UpdateBookingBlockControllerDTO.Response.Body updateBookingBlock(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id,
            @Valid @RequestBody UpdateBookingBlockControllerDTO.Request.Body requestBody
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            UpdateBookingBlockUseCaseDTO.Params useCaseParamsDTO = new UpdateBookingBlockUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId,
                    new UpdateBookingBlockUseCaseDTO.Params.Payload(
                            requestBody.checkinDate,
                            requestBody.checkoutDate
                    )
            );

            UpdateBookingBlockUseCaseDTO.Result useCaseResultDTO = updateBookingBlockUseCase.execute(useCaseParamsDTO);

            return new UpdateBookingBlockControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking block not found");
        } catch (BookingDateUnavailableException exception){
            String message = String.format("A Booking already exists at this Property between the dates %s and %s", exception.payload.checkinDate, exception.payload.checkoutDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Secured({UserEntity.UserType.MapValue.HOST})
    public DeleteBookingBlockControllerDTO.Response.Body deleteBookingBlock(
            Authentication authentication,
            @PathVariable @NotNull @Min(0) int id
    ) {
        try {
            AuthTokenPayloadDTO authTokenPayload = (AuthTokenPayloadDTO) authentication.getPrincipal();

            DeleteBookingBlockUseCaseDTO.Params useCaseParamsDTO = new DeleteBookingBlockUseCaseDTO.Params(
                    id,
                    authTokenPayload.userId
            );

            DeleteBookingBlockUseCaseDTO.Result useCaseResultDTO = deleteBookingBlockUseCase.execute(useCaseParamsDTO);

            return new DeleteBookingBlockControllerDTO.Response.Body(
                    useCaseResultDTO.id
            );
        } catch (BookingNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }


}
