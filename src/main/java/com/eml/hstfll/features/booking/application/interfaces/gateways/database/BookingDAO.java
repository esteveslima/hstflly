package com.eml.hstfll.features.booking.application.interfaces.gateways.database;


import com.eml.hstfll.features.booking.application.exceptions.BookingDateUnavailableException;
import com.eml.hstfll.features.booking.application.exceptions.BookingNotFoundException;
import com.eml.hstfll.features.booking.domain.entities.BookingEntity;
import jakarta.persistence.EntityManager;

import java.util.Date;

public interface BookingDAO {

    public BookingEntity registerBooking(BookingEntity entity) throws BookingDateUnavailableException;

    public BookingEntity findBookingById(Integer id, Integer guestOrHostUserId) throws BookingNotFoundException;

    public BookingEntity updateBooking(BookingEntity entity, Integer guestUserId) throws BookingNotFoundException, BookingDateUnavailableException;

    public void cancelBooking(Integer id, Integer guestUserId) throws BookingNotFoundException;

    public void rebookCancelledBooking(BookingEntity entity, Integer guestUserId) throws BookingNotFoundException, BookingDateUnavailableException;

    public void deleteBooking(Integer id, Integer guestUserId) throws BookingNotFoundException;

    //

    public BookingEntity registerBookingBlock(BookingEntity entity) throws BookingDateUnavailableException;

    public BookingEntity updateBookingBlock(BookingEntity entity, Integer hostUserId) throws BookingNotFoundException, BookingDateUnavailableException;

    public void deleteBookingBlock(Integer id, Integer hostUserId) throws BookingNotFoundException;
}
