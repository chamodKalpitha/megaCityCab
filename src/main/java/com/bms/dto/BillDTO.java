package com.bms.dto;

import com.bms.enums.PaymentMethod;

public class BillDTO {
	private int billId;
	private int bookingId;
	private double totalKm;
	private int totalDays;
	private double totalAmount;
	private double totalTax;
	private PaymentMethod paymentMethod;
	private BookingDTO bookingDTO;
	private VehicleDTO vehicleDTO;
	
	public BillDTO(double totalAmount, double totalTax) {
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
	}

	public BillDTO(int billId, int bookingId, double totalKm, double totalAmount, double totalTax,
			PaymentMethod paymentMethod) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalKm = totalKm;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
	}
	
	
	public BillDTO(int billId, int bookingId, double totalKm, double totalAmount, double totalTax,
			PaymentMethod paymentMethod, BookingDTO bookingDTO, VehicleDTO vehicleDTO) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalKm = totalKm;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
		this.bookingDTO = bookingDTO;
		this.vehicleDTO = vehicleDTO;
	}

	public BillDTO(int billId, int bookingId, int totalDays, double totalAmount, double totalTax,
			PaymentMethod paymentMethod) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalDays = totalDays;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
	}
	
	
	public BillDTO(int billId, int bookingId, int totalDays, double totalAmount, double totalTax,
			PaymentMethod paymentMethod, BookingDTO bookingDTO, VehicleDTO vehicleDTO) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalDays = totalDays;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
		this.bookingDTO = bookingDTO;
		this.vehicleDTO = vehicleDTO;
	}

	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public double getTotalKm() {
		return totalKm;
	}
	public void setTotalKm(double totalKm) {
		this.totalKm = totalKm;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BookingDTO getBookingDTO() {
		return bookingDTO;
	}

	public void setBookingDTO(BookingDTO bookingDTO) {
		this.bookingDTO = bookingDTO;
	}

	public VehicleDTO getVehicleDTO() {
		return vehicleDTO;
	}

	public void setVehicleDTO(VehicleDTO vehicleDTO) {
		this.vehicleDTO = vehicleDTO;
	}
	
}
