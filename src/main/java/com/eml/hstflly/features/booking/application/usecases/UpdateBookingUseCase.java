package com.eml.hstflly.features.booking.application.usecases;

import com.eml.hstflly.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstflly.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.booking.application.interfaces.usecases.dtos.UpdateBookingUseCaseDTO;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("updateBookingUseCase")
public class UpdateBookingUseCase implements UseCase<UpdateBookingUseCaseDTO.Params, UpdateBookingUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public UpdateBookingUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public UpdateBookingUseCaseDTO.Result execute(UpdateBookingUseCaseDTO.Params params) {
        BookingEntity bookingToUpdate = this.bookingDAO.findBookingById(params.id, params.guestUserId);
        if(params.payload.arrivalHour != null) bookingToUpdate.setArrivalHour(params.payload.arrivalHour);
        if(params.payload.guestsNumber != null) bookingToUpdate.setGuestsNumber(params.payload.guestsNumber);
        if(params.payload.checkinDate != null) bookingToUpdate.setCheckinDate(params.payload.checkinDate);
        if(params.payload.checkoutDate != null) bookingToUpdate.setCheckoutDate(params.payload.checkoutDate);

        BookingEntity updatedBooking = this.bookingDAO.updateBooking(bookingToUpdate, params.guestUserId);

        return new UpdateBookingUseCaseDTO.Result(updatedBooking.getId());
    }

}
