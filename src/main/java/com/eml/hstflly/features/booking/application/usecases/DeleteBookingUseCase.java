package com.eml.hstflly.features.booking.application.usecases;

import com.eml.hstflly.features.booking.application.exceptions.BookingStillEnabledException;
import com.eml.hstflly.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstflly.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.booking.application.interfaces.usecases.dtos.DeleteBookingUseCaseDTO;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("deleteBookingUseCase")
public class DeleteBookingUseCase implements UseCase<DeleteBookingUseCaseDTO.Params, DeleteBookingUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public DeleteBookingUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public DeleteBookingUseCaseDTO.Result execute(DeleteBookingUseCaseDTO.Params params) {
        BookingEntity entity = this.bookingDAO.findBookingById(params.id, params.guestUserId);

        boolean isBookingCancelled = entity.status == BookingEntity.BookingStatus.CANCELLED;
        if(!isBookingCancelled) {
            throw new BookingStillEnabledException();
        }

        this.bookingDAO.deleteBooking(entity.getId(), params.guestUserId);

        return new DeleteBookingUseCaseDTO.Result(entity.getId());
    }

}
