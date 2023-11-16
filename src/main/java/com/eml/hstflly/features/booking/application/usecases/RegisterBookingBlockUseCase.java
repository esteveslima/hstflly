package com.eml.hstflly.features.booking.application.usecases;

import com.eml.hstflly.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstflly.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.booking.application.interfaces.usecases.dtos.RegisterBookingBlockUseCaseDTO;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
import com.eml.hstflly.features.property.domain.entities.PropertyEntity;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("registerBookingBlockUseCase")
public class RegisterBookingBlockUseCase implements UseCase<RegisterBookingBlockUseCaseDTO.Params, RegisterBookingBlockUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public RegisterBookingBlockUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public RegisterBookingBlockUseCaseDTO.Result execute(RegisterBookingBlockUseCaseDTO.Params params) {
        BookingEntity entity = new BookingEntity();
        UserEntity hostEntity = new UserEntity();
        hostEntity.setId(params.hostUserId);
        entity.setUser(hostEntity);
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(params.propertyId);
        entity.setProperty(propertyEntity);
        entity.setCheckinDate(params.payload.checkinDate);
        entity.setCheckoutDate(params.payload.checkoutDate);

        BookingEntity createdBooking = this.bookingDAO.registerBookingBlock(entity);

        return new RegisterBookingBlockUseCaseDTO.Result(createdBooking.getId());
    }

}
