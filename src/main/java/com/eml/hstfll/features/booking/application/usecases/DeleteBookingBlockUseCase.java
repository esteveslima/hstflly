package com.eml.hstfll.features.booking.application.usecases;

import com.eml.hstfll.features.booking.application.exceptions.BookingStillEnabledException;
import com.eml.hstfll.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstfll.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstfll.features.booking.application.interfaces.usecases.dtos.DeleteBookingBlockUseCaseDTO;
import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("deleteBookingBlockUseCase")
public class DeleteBookingBlockUseCase implements UseCase<DeleteBookingBlockUseCaseDTO.Params, DeleteBookingBlockUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public DeleteBookingBlockUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public DeleteBookingBlockUseCaseDTO.Result execute(DeleteBookingBlockUseCaseDTO.Params params) {
        this.bookingDAO.deleteBookingBlock(params.id, params.hostUserId);

        return new DeleteBookingBlockUseCaseDTO.Result(params.id);
    }

}
