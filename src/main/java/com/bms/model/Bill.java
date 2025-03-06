package com.bms.model;

import com.bms.enums.PaymentMethod;

public class Bill {
	private int billId;
	private int bookingId;
	private double totalKm;
	private int totalDays;
	private double totalAmount;
	private double totalTax;
	private PaymentMethod paymentMethod;
	
	public Bill(int billId, int bookingId, double totalKm, double totalAmount, double totalTax,
			PaymentMethod paymentMethod) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalKm = totalKm;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
	}

	public Bill(int billId, int bookingId, int totalDays, double totalAmount, double totalTax,
			PaymentMethod paymentMethod) {
		super();
		this.billId = billId;
		this.bookingId = bookingId;
		this.totalDays = totalDays;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
	}
	
	
	
	public Bill( int bookingId, double totalKm, int totalDays, double totalAmount, double totalTax,
			PaymentMethod paymentMethod) {
		this.bookingId = bookingId;
		this.totalKm = totalKm;
		this.totalDays = totalDays;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.paymentMethod = paymentMethod;
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
	
}
