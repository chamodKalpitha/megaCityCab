package com.bms.dto;

import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.model.Customer;
import com.bms.model.Vehicle;

import java.util.Date;

public class BookingDTO {
    private int bookingId;
    private int customerId;
    private int bookedVehicleId;
    private Integer driverId;
    private Date bookingDate;
    private BookingStatus bookingStatus;
    private PricingType pricingType;
    private VehicleDTO vehicleDTO;
    private CustomerDTO customerDTO;

    public BookingDTO() {};
    
	public BookingDTO(int bookingId, int bookedVehicleId, Integer driverId, Date bookingDate,
			BookingStatus bookingStatus, PricingType pricingType) {
		super();
		this.bookingId = bookingId;
		this.bookedVehicleId = bookedVehicleId;
		this.driverId = driverId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
	}

	public BookingDTO(int bookingId, int bookedVehicleId, Date bookingDate, BookingStatus bookingStatus,
			PricingType pricingType) {
		super();
		this.bookingId = bookingId;
		this.bookedVehicleId = bookedVehicleId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
	}

	public BookingDTO(int bookingId, int customerId, int bookedVehicleId, Integer driverId, Date bookingDate, 
                      BookingStatus bookingStatus, PricingType pricingType) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.bookedVehicleId = bookedVehicleId;
        this.driverId = driverId;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.pricingType = pricingType;
    }
    
    public BookingDTO(int bookingId, int customerId, int bookedVehicleId, Integer driverId, Date bookingDate,
			BookingStatus bookingStatus, PricingType pricingType, VehicleDTO vehicleDTO, CustomerDTO customerDTO) {
		super();
		this.bookingId = bookingId;
		this.customerId = customerId;
		this.bookedVehicleId = bookedVehicleId;
		this.driverId = driverId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
		this.vehicleDTO = vehicleDTO;
		this.customerDTO = customerDTO;
	}

	public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

	public VehicleDTO getVehicleDTO() {
		return vehicleDTO;
	}

	public void setVehicleDTO(VehicleDTO vehicleDTO) {
		this.vehicleDTO = vehicleDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
    
}
