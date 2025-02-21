package com.bms.controller;

import com.bms.dao.BookingDAO;
import com.bms.dto.BookingDTO;
import com.bms.enums.PricingType;
import com.bms.model.Booking;

import java.sql.SQLException;
import java.util.List;

public class BookingController {
    private final BookingDAO bookingDAO;

    public BookingController(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public boolean createBooking(BookingDTO BookingDTO) throws SQLException {
    	Booking booking = new Booking(BookingDTO.getCustomerId(),BookingDTO.getBookedVehicleId(), BookingDTO.getBookingDate(), BookingDTO.getBookingStatus(), BookingDTO.getPricingType());
        return bookingDAO.createBooking(booking);
    }

    public List<BookingDTO> getBookings(String search, int limit, int offset) throws SQLException {
        return bookingDAO.getBookings(search, limit, offset);
    }

    public int getBookingsCount(String search) throws SQLException {
        return bookingDAO.getBookingsCount(search);
    }

    public BookingDTO getBookingById(int bookingId) throws SQLException {
        return bookingDAO.getBookingById(bookingId);
    }

    public boolean updateBooking(BookingDTO BookingDTO) throws SQLException {
    	Booking booking;
    	
    	if (BookingDTO.getPricingType() == PricingType.PER_DAY_WITH_DRIVER || BookingDTO.getPricingType() == PricingType.PER_KM_WITH_DRIVER) {
            booking = new Booking(BookingDTO.getBookingId(),BookingDTO.getBookedVehicleId(),BookingDTO.getDriverId(),BookingDTO.getBookingDate(),BookingDTO.getBookingStatus(),BookingDTO.getPricingType());
        } else {
        	booking = new Booking(BookingDTO.getBookingId(),BookingDTO.getBookedVehicleId(),BookingDTO.getBookingDate(),BookingDTO.getBookingStatus(),BookingDTO.getPricingType());
        }
    	
        return bookingDAO.updateBooking(booking);
    }

    public boolean deleteBooking(int bookingId) throws SQLException {
        return bookingDAO.deleteBooking(bookingId);
    }
}
