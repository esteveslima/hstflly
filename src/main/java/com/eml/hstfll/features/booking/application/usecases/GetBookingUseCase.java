package com.eml.hstfll.features.booking.application.usecases;

import com.eml.hstfll.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstfll.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.booking.application.interfaces.usecases.dtos.GetBookingUseCaseDTO;
import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("getBookingUseCase")
public class GetBookingUseCase implements UseCase<GetBookingUseCaseDTO.Params, GetBookingUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public GetBookingUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public GetBookingUseCaseDTO.Result execute(GetBookingUseCaseDTO.Params params) {
        BookingEntity entityFound = this.bookingDAO.findBookingById(params.id, params.guestOrHostUserId);

        return new GetBookingUseCaseDTO.Result(
                entityFound.getProperty().getId(),
                entityFound.getGuestsNumber(),
                entityFound.getArrivalHour(),
                entityFound.getCheckinDate(),
                entityFound.getCheckoutDate(),
                entityFound.status
        );
    }

}
