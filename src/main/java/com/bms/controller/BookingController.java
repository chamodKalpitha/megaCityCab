package com.bms.controller;

import com.bms.dao.BookingDAO;
import com.bms.dto.BookingDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.model.Booking;
import com.bms.observer.BookingObserver;
import com.bms.observer.BookingSubject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingController implements BookingSubject {
    private final BookingDAO bookingDAO;
    private final List<BookingObserver> observers = new ArrayList<>();


    public BookingController(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
    

	@Override
	public void registerObserver(BookingObserver observer) {
		 observers.add(observer);
	}
	
    private void notifyObservers(BookingDTO bookingDTO) {

        for (BookingObserver observer : observers) {
            observer.onBookingCreated(bookingDTO);
        }
    }

    public boolean createBooking(BookingDTO BookingDTO) throws SQLException {
    	  	
    	Booking booking = new Booking();
    	booking.setUserId(BookingDTO.getUserId());
    	booking.setBookedVehicleId(BookingDTO.getBookedVehicleId());
    	booking.setBookingDate(BookingDTO.getBookingDate());
    	booking.setBookingStatus(BookingStatus.PENDING);
    	booking.setPricingType(BookingDTO.getPricingType());
    	BookingDTO bookingIdDto = bookingDAO.createBooking(booking);
    	int bookingId = bookingIdDto.getBookingId();
        if(bookingId > 0) {
            BookingDTO createdBooking = bookingDAO.getBookingById(bookingId);
            notifyObservers(createdBooking);
            return true;
        }
        
        return false;
    }

    public List<BookingDTO> getBookings(String search, int limit, int offset) throws SQLException {
        return bookingDAO.getBookings(search, limit, offset);
    }
    
    public List<BookingDTO> getBookingsByCustomer(String search, int limit, int offset, int customerId) throws SQLException {
        return bookingDAO.getBookingsByCustomer(search, limit, offset,customerId);
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
            booking = new Booking(BookingDTO.getBookingId(),BookingDTO.getBookedVehicleId(),null,BookingDTO.getBookingDate(),BookingDTO.getBookingStatus(),BookingDTO.getPricingType());
        }
    	
        return bookingDAO.updateBooking(booking);
    }

    public boolean deleteBooking(int bookingId) throws SQLException {
        return bookingDAO.deleteBooking(bookingId);
    }

    public int getBookingsCountByCustomer(String search, int customerId) throws SQLException {
        return bookingDAO.getBookingsCountByCustomer(search,customerId);
    }
    
    public boolean updateBookingByCustomer(BookingDTO BookingDTO) throws SQLException {
    	Booking booking;
    	
    	if (BookingDTO.getPricingType() == PricingType.PER_DAY_WITH_DRIVER || BookingDTO.getPricingType() == PricingType.PER_KM_WITH_DRIVER) {
            booking = new Booking(BookingDTO.getBookingId(),BookingDTO.getBookedVehicleId(),BookingDTO.getDriverId(),BookingDTO.getBookingDate(),BookingDTO.getBookingStatus(),BookingDTO.getPricingType());
        } else {
            booking = new Booking(BookingDTO.getBookingId(),BookingDTO.getBookedVehicleId(),null,BookingDTO.getBookingDate(),BookingDTO.getBookingStatus(),BookingDTO.getPricingType());
        }
    	
        return bookingDAO.updateBookingByCustomer(booking);
    }
    
    public boolean calcelBooking(int bookingId) throws SQLException {
        return bookingDAO.calcelBooking(bookingId);
    }

}
