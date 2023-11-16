package com.eml.hstflly.features.booking.application.interfaces.gateways.database;


import com.eml.hstflly.features.booking.application.exceptions.BookingDateUnavailableException;
import com.eml.hstflly.features.booking.application.exceptions.BookingNotFoundException;
import com.eml.hstflly.features.booking.domain.entities.BookingEntity;

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
