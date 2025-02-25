package com.bms.model;

import java.util.Date;

import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;

public class Booking {
	private int bookingId;
	private int userId;
	private int bookedVehicleId;
	private Integer driverId;
	private Date bookingDate;
	private BookingStatus bookingStatus;
	private PricingType pricingType;
	private boolean is_delete;
	
	
	public Booking() {
	}

	public Booking(int bookingId, int bookedVehicleId, Integer driverId, Date bookingDate, BookingStatus bookingStatus,
			PricingType pricingType) {
		super();
		this.bookingId = bookingId;
		this.bookedVehicleId = bookedVehicleId;
		this.driverId = driverId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
	}


	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookedVehicleId() {
		return bookedVehicleId;
	}

	public void setBookedVehicleId(int bookedVehicleId) {
		this.bookedVehicleId = bookedVehicleId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public PricingType getPricingType() {
		return pricingType;
	}

	public void setPricingType(PricingType pricingType) {
		this.pricingType = pricingType;
	}

	public boolean isIs_delete() {
		return is_delete;
	}

	public void setIs_delete(boolean is_delete) {
		this.is_delete = is_delete;
	}
	
	
}
