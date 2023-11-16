package com.eml.hstflly.features.booking.adapters.gateways.database.daos;

import com.eml.hstflly.features.booking.application.exceptions.BookingDateUnavailableException;
import com.eml.hstflly.features.booking.application.exceptions.BookingNotFoundException;
import com.eml.hstflly.features.booking.application.exceptions.BookingPropertyNotFoundException;
import com.eml.hstflly.features.booking.application.interfaces.gateways.database.BookingDAO;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Repository
@Qualifier("bookingJpaDAO")
public class BookingJpaDAO implements BookingDAO {

    private EntityManager entityManager;

    @Autowired
    public BookingJpaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public BookingEntity registerBooking(BookingEntity entity) throws BookingDateUnavailableException {
        int idInsertOperation = 0;

        boolean hasBookingDateConflict = this.hasDateRangeOverlap(
                entityManager,
                entity.getProperty().getId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                idInsertOperation
        );
        if(hasBookingDateConflict){
            throw new BookingDateUnavailableException(
                    new BookingDateUnavailableException.ExceptionPayload(entity.getCheckinDate(), entity.getCheckoutDate())
            );
        }

        try {
            entity.setStatus(BookingEntity.BookingStatus.ENABLED);
            entity.setType(BookingEntity.BookingType.BOOKING);

            entity.setId(idInsertOperation);

            BookingEntity result = this.entityManager.merge(entity);

            return result;
        } catch (EntityNotFoundException exception) {
            throw new BookingPropertyNotFoundException();
        }
    }

    @Override
    public BookingEntity findBookingById(Integer id, Integer guestOrHostUserId) throws BookingNotFoundException {
        String jpqlQuery = """
            SELECT bookings
            FROM BookingEntity bookings
            WHERE 
            bookings.id = :idValue AND
            (bookings.user.id = :guestOrHostUserIdValue OR bookings.property.hostUser.id = :guestOrHostUserIdValue)
        """;

        TypedQuery<BookingEntity> typedQuery = entityManager.createQuery(jpqlQuery,BookingEntity.class)
                .setParameter("idValue", id)
                .setParameter("guestOrHostUserIdValue", guestOrHostUserId);

        try{
            BookingEntity result = typedQuery.getSingleResult();
            return result;
        } catch(NoResultException exception) {
            throw new BookingNotFoundException();
        }
    }

    @Override
    @Transactional
    public BookingEntity updateBooking(BookingEntity entity, Integer guestUserId) throws BookingNotFoundException, BookingDateUnavailableException {
        boolean hasBookingDateConflict = this.hasDateRangeOverlap(
                entityManager,
                entity.getProperty().getId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getId()
        );
        if(hasBookingDateConflict){
            throw new BookingDateUnavailableException(
                    new BookingDateUnavailableException.ExceptionPayload(entity.getCheckinDate(), entity.getCheckoutDate())
            );
        }

        String jpqlQuery = """
            UPDATE BookingEntity bookings
            SET
            bookings.guestsNumber = :guestsNumberValue,
            bookings.arrivalHour = :arrivalHourValue,
            bookings.checkinDate = :checkinDateValue,
            bookings.checkoutDate = :checkoutDateValue
            WHERE
            bookings.id = :idValue AND
            bookings.user.id = :guestUserIdValue AND
            bookings.type = BOOKING
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", entity.getId())
                .setParameter("guestUserIdValue", guestUserId)
                .setParameter("guestsNumberValue", entity.getGuestsNumber())
                .setParameter("arrivalHourValue", entity.getArrivalHour())
                .setParameter("checkinDateValue", entity.getCheckinDate())
                .setParameter("checkoutDateValue", entity.getCheckoutDate());

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return entity;
    }

    @Override
    public void cancelBooking(Integer id, Integer guestUserId) throws BookingNotFoundException {
        String jpqlQuery = """
            UPDATE BookingEntity bookings
            SET bookings.status = CANCELLED
            WHERE
            bookings.id = :idValue AND
            bookings.user.id = :guestUserIdValue AND
            bookings.type = BOOKING
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", id)
                .setParameter("guestUserIdValue", guestUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return;
    }

    @Override
    @Transactional
    public void rebookCancelledBooking(BookingEntity entity, Integer guestUserId) throws BookingNotFoundException, BookingDateUnavailableException {
        boolean hasBookingDateConflict = this.hasDateRangeOverlap(
                entityManager,
                entity.getProperty().getId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getId()
        );
        if(hasBookingDateConflict){
            throw new BookingDateUnavailableException(
                    new BookingDateUnavailableException.ExceptionPayload(entity.getCheckinDate(), entity.getCheckoutDate())
            );
        }

        String jpqlQuery = """
            UPDATE BookingEntity bookings
            SET bookings.status = ENABLED
            WHERE 
            bookings.id = :idValue AND 
            bookings.user.id = :guestUserIdValue AND
            bookings.type = BOOKING
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", entity.getId())
                .setParameter("guestUserIdValue", guestUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return;
    }

    @Override
    public void deleteBooking(Integer id, Integer guestUserId) throws BookingNotFoundException {
        String jpqlQuery = """
            DELETE FROM BookingEntity bookings
            WHERE
            bookings.id = :idValue AND
            bookings.user.id = :guestUserIdValue AND
            bookings.type = BOOKING
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", id)
                .setParameter("guestUserIdValue", guestUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return;
    }

    //

    @Override
    @Transactional
    public BookingEntity registerBookingBlock(BookingEntity entity) throws BookingDateUnavailableException {
        boolean hasBookingDateConflict = this.hasDateRangeOverlap(
                entityManager,
                entity.getProperty().getId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getId()
        );
        if(hasBookingDateConflict){
            throw new BookingDateUnavailableException(
                    new BookingDateUnavailableException.ExceptionPayload(entity.getCheckinDate(), entity.getCheckoutDate())
            );
        }

        entity.setGuestsNumber(null);
        entity.setArrivalHour(null);
        entity.setStatus(BookingEntity.BookingStatus.ENABLED);
        entity.setType(BookingEntity.BookingType.BLOCK);

        int idInsertOperation = 0;
        entity.setId(idInsertOperation);

        BookingEntity result = this.entityManager.merge(entity);

        return result;
    }

    @Override
    @Transactional
    public BookingEntity updateBookingBlock(BookingEntity entity, Integer hostUserId) throws BookingNotFoundException, BookingDateUnavailableException {
        boolean hasBookingDateConflict = this.hasDateRangeOverlap(
                entityManager,
                entity.getProperty().getId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getId()
        );
        if(hasBookingDateConflict){
            throw new BookingDateUnavailableException(
                    new BookingDateUnavailableException.ExceptionPayload(entity.getCheckinDate(), entity.getCheckoutDate())
            );
        }

        String jpqlQuery = """
            UPDATE BookingEntity bookings
            SET
            bookings.checkinDate = :checkinDateValue,
            bookings.checkoutDate = :checkoutDateValue
            WHERE
            bookings.id = :idValue AND
            bookings.user.id = :hostUserIdValue AND
            bookings.type = BLOCK
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", entity.getId())
                .setParameter("hostUserIdValue", hostUserId)
                .setParameter("checkinDateValue", entity.getCheckinDate())
                .setParameter("checkoutDateValue", entity.getCheckoutDate());

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return entity;
    }

    @Override
    public void deleteBookingBlock(Integer id, Integer hostUserId) throws BookingNotFoundException {
        String jpqlQuery = """
            DELETE FROM BookingEntity bookings
            WHERE
            bookings.id = :idValue AND
            bookings.user.id = :hostUserIdValue AND
            bookings.type = BLOCK
        """;

        Query query = entityManager.createQuery(jpqlQuery)
                .setParameter("idValue", id)
                .setParameter("hostUserIdValue", hostUserId);

        int updatedAmount = query.executeUpdate();

        boolean isUpdateSuccessful = updatedAmount == 1;
        if(!isUpdateSuccessful){
            throw new BookingNotFoundException();
        }

        return;
    }

    //

    private boolean hasDateRangeOverlap(EntityManager entityManager, Integer propertyId, Date checkinDate, Date checkoutDate, Integer excludedBookingId) {
        String jpqlQuery = """
            SELECT COUNT(b) 
            FROM BookingEntity b 
            WHERE 
            :checkinDateValue < b.checkoutDate AND
            :checkoutDateValue > b.checkinDate AND
            b.property.id = :propertIdValue AND
            b.id != :excludedBookingIdValue AND
            b.status != CANCELLED    
        """;
        TypedQuery<Long> query = entityManager.createQuery(jpqlQuery, Long.class)
                .setParameter("checkinDateValue", checkinDate)
                .setParameter("checkoutDateValue", checkoutDate)
                .setParameter("propertIdValue", propertyId)
                .setParameter("excludedBookingIdValue", excludedBookingId);

        boolean hasOverlap = query.getSingleResult() > 0;
        return hasOverlap;
    }
}