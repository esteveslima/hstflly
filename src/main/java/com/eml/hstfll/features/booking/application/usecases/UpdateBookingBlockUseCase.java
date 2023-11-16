package com.eml.hstfll.features.booking.application.usecases;

import com.eml.hstfll.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstfll.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.booking.application.interfaces.usecases.dtos.UpdateBookingBlockUseCaseDTO;
import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("updateBookingBlockUseCase")
public class UpdateBookingBlockUseCase implements UseCase<UpdateBookingBlockUseCaseDTO.Params, UpdateBookingBlockUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public UpdateBookingBlockUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public UpdateBookingBlockUseCaseDTO.Result execute(UpdateBookingBlockUseCaseDTO.Params params) {
        BookingEntity bookingBlockToUpdate = this.bookingDAO.findBookingById(params.id, params.hostUserId);
        if(params.payload.checkinDate != null) bookingBlockToUpdate.setCheckinDate(params.payload.checkinDate);
        if(params.payload.checkoutDate != null) bookingBlockToUpdate.setCheckoutDate(params.payload.checkoutDate);

        BookingEntity updatedBookingBlock = this.bookingDAO.updateBookingBlock(bookingBlockToUpdate, params.hostUserId);

        return new UpdateBookingBlockUseCaseDTO.Result(updatedBookingBlock.getId());
    }

}
