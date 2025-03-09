package com.bms.dto;

import com.bms.enums.PaymentMethod;

public class BillCalculateDateDTO {
	private BookingDTO bookingDTO;
	private double kmCount;
	private long dayCout;
	private double driverDaySalary;
	private double driverKmSalary;
	private VehicleDTO vehicleDTO;
	private PaymentMethod paymentMethod;
	

	
	public BookingDTO getBookingDTO() {
		return bookingDTO;
	}
	public void setBookingDTO(BookingDTO bookingDTO) {
		this.bookingDTO = bookingDTO;
	}
	public double getKmCount() {
		return kmCount;
	}
	public void setKmCount(double kmCount) {
		this.kmCount = kmCount;
	}
	public long getDayCout() {
		return dayCout;
	}
	public void setDayCout(long dayCout) {
		this.dayCout = dayCout;
	}
	public double getDriverDaySalary() {
		return driverDaySalary;
	}
	public void setDriverDaySalary(double driverDaySalary) {
		this.driverDaySalary = driverDaySalary;
	}
	public double getDriverKmSalary() {
		return driverKmSalary;
	}
	public void setDriverKmSalary(double driverKmSalary) {
		this.driverKmSalary = driverKmSalary;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public VehicleDTO getVehicleDTO() {
		return vehicleDTO;
	}
	public void setVehicleDTO(VehicleDTO vehicleDTO) {
		this.vehicleDTO = vehicleDTO;
	}
	
	

}
