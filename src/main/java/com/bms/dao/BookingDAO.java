package com.bms.dao;

import com.bms.dto.BookingDTO;
import com.bms.model.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO {
	BookingDTO createBooking(Booking booking) throws SQLException;

    List<BookingDTO> getBookings(String search, int limit, int offset) throws SQLException;

    int getBookingsCount(String search) throws SQLException;

    BookingDTO getBookingById(int bookingId) throws SQLException;

    boolean updateBooking(Booking booking) throws SQLException;

    boolean deleteBooking(int bookingId) throws SQLException;
}
