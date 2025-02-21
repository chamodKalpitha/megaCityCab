package com.bms.dto;

public class CustomerDTO {
	private int customerId;
	private String customerName;
	private String address;
	private String nicNumber;
	private String contactNumber;
    private boolean isDelete;
	
	public CustomerDTO() {
	}
	
	

	public CustomerDTO(int customerId, String customerName, String address, String nicNumber, String contactNumber) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.nicNumber = nicNumber;
		this.contactNumber = contactNumber;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNicNumber() {
		return nicNumber;
	}

	public void setNicNumber(String nicNumber) {
		this.nicNumber = nicNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
