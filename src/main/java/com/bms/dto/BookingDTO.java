package com.bms.dto;

import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;

import java.util.Date;

public class BookingDTO {
    private int bookingId;
    private int userId;
    private int bookedVehicleId;
    private Integer driverId;
    private Date bookingDate;
    private BookingStatus bookingStatus;
    private PricingType pricingType;
    private VehicleDTO vehicleDTO;
    private CustomerDTO customerDTO;
    private UserDTO userDTO;

    public BookingDTO() {};
    
   	public BookingDTO(int bookingId) {
		super();
		this.bookingId = bookingId;
	}

	public BookingDTO(int userId, int bookedVehicleId, Date bookingDate, PricingType pricingType) {
		super();
		this.userId = userId;
		this.bookedVehicleId = bookedVehicleId;
		this.bookingDate = bookingDate;
		this.pricingType = pricingType;
	}

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

	public BookingDTO(int bookingId, int userId, int bookedVehicleId, Integer driverId, Date bookingDate, 
                      BookingStatus bookingStatus, PricingType pricingType) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bookedVehicleId = bookedVehicleId;
        this.driverId = driverId;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.pricingType = pricingType;
    }
    
    public BookingDTO(int bookingId, int userId, int bookedVehicleId, Integer driverId, Date bookingDate,
			BookingStatus bookingStatus, PricingType pricingType, VehicleDTO vehicleDTO, CustomerDTO customerDTO) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.bookedVehicleId = bookedVehicleId;
		this.driverId = driverId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
		this.vehicleDTO = vehicleDTO;
		this.customerDTO = customerDTO;
	}
    
    

	public BookingDTO(int bookingId, int userId, int bookedVehicleId, Integer driverId, Date bookingDate,
			BookingStatus bookingStatus, PricingType pricingType, VehicleDTO vehicleDTO, CustomerDTO customerDTO,
			UserDTO userDTO) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.bookedVehicleId = bookedVehicleId;
		this.driverId = driverId;
		this.bookingDate = bookingDate;
		this.bookingStatus = bookingStatus;
		this.pricingType = pricingType;
		this.vehicleDTO = vehicleDTO;
		this.customerDTO = customerDTO;
		this.userDTO = userDTO;
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

    public void setUserId(int customerId) {
        this.userId = customerId;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
    
}
