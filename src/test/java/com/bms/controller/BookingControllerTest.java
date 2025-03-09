package com.bms.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bms.dao.BookingDAO;
import com.bms.dto.BookingDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.model.Booking;
import com.bms.observer.BookingObserver;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingDAO bookingDAO;

    @Mock
    private BookingObserver observer;

    @InjectMocks
    private BookingController bookingController;

    private BookingDTO bookingDTO;
    private Booking booking;

    @BeforeEach
    void setUp() {
        bookingDTO = new BookingDTO();
        bookingDTO.setUserId(1);
        bookingDTO.setBookedVehicleId(4);
        bookingDTO.setBookingDate(new Date());
        bookingDTO.setPricingType(PricingType.PER_KM_WITH_DRIVER);
        bookingDTO.setBookingId(1);
        bookingDTO.setBookingStatus(BookingStatus.PENDING);

        booking = new Booking();
        booking.setUserId(1);
        booking.setBookedVehicleId(4);
        booking.setBookingDate(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPricingType(PricingType.PER_KM_WITH_DRIVER);
    }

    @Test
    void testCreateBooking_Success() throws SQLException {
        when(bookingDAO.createBooking(any(Booking.class))).thenReturn(bookingDTO);
        when(bookingDAO.getBookingById(1)).thenReturn(bookingDTO);
        
        bookingController.registerObserver(observer);
        boolean result = bookingController.createBooking(bookingDTO);

        assertTrue(result);
        verify(observer, times(1)).onBookingCreated(bookingDTO);
    }

    @Test
    void testGetBookings() throws SQLException {
        List<BookingDTO> bookings = Arrays.asList(bookingDTO);
        when(bookingDAO.getBookings("", 10, 0)).thenReturn(bookings);

        List<BookingDTO> result = bookingController.getBookings("", 10, 0);

        assertEquals(bookings, result);
    }

    @Test
    void testUpdateBooking() throws SQLException {
        when(bookingDAO.updateBooking(any(Booking.class))).thenReturn(true);

        boolean result = bookingController.updateBooking(bookingDTO);

        assertTrue(result);
    }

    @Test
    void testDeleteBooking() throws SQLException {
        when(bookingDAO.deleteBooking(1)).thenReturn(true);

        boolean result = bookingController.deleteBooking(1);

        assertTrue(result);
    }

    @Test
    void testGetBookingById() throws SQLException {
        when(bookingDAO.getBookingById(1)).thenReturn(bookingDTO);

        BookingDTO result = bookingController.getBookingById(1);

        assertEquals(bookingDTO, result);
    }
}
