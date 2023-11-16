package com.eml.hstfll.features.booking.application.usecases;

import com.eml.hstfll.features.booking.application.exceptions.BookingNotEnabledException;
import com.eml.hstfll.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstfll.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.booking.application.interfaces.usecases.dtos.CancelBookingUseCaseDTO;
import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("cancelBookingUseCase")
public class CancelBookingUseCase implements UseCase<CancelBookingUseCaseDTO.Params, CancelBookingUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public CancelBookingUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public CancelBookingUseCaseDTO.Result execute(CancelBookingUseCaseDTO.Params params) {
        BookingEntity entity = this.bookingDAO.findBookingById(params.id, params.guestUserId);

        boolean isBookingEnabled = entity.status == BookingEntity.BookingStatus.ENABLED;
        if(!isBookingEnabled) {
            throw new BookingNotEnabledException();
        }

        this.bookingDAO.cancelBooking(entity.getId(), params.guestUserId);

        return new CancelBookingUseCaseDTO.Result(entity.getId());
    }

}
