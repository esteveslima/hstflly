package com.eml.hstflly.features.booking.application.usecases;

import com.eml.hstflly.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstflly.features.booking.application.interfaces.usecases.UseCase;
import com.eml.hstflly.features.booking.application.interfaces.usecases.dtos.RegisterBookingUseCaseDTO;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
import com.eml.hstflly.features.property.domain.entities.PropertyEntity;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("registerBookingUseCase")
public class RegisterBookingUseCase implements UseCase<RegisterBookingUseCaseDTO.Params, RegisterBookingUseCaseDTO.Result> {

    private BookingDAO bookingDAO;

    @Autowired
    public RegisterBookingUseCase(@Qualifier("bookingJpaDAO") BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Transactional
    public RegisterBookingUseCaseDTO.Result execute(RegisterBookingUseCaseDTO.Params params) {
        BookingEntity entity = new BookingEntity();
        UserEntity guestEntity = new UserEntity();
        guestEntity.setId(params.guestUserId);
        entity.setUser(guestEntity);
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(params.propertyId);
        entity.setProperty(propertyEntity);
        entity.setGuestsNumber(params.payload.guestsNumber);
        entity.setArrivalHour(params.payload.arrivalHour);
        entity.setCheckinDate(params.payload.checkinDate);
        entity.setCheckoutDate(params.payload.checkoutDate);

        BookingEntity createdBooking = this.bookingDAO.registerBooking(entity);

        return new RegisterBookingUseCaseDTO.Result(createdBooking.getId());
    }

}
