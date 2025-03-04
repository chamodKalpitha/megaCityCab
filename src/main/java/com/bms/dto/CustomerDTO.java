package com.bms.dto;

public class CustomerDTO {
    private int customerId;
    private String customerName;
    private String address;
    private String nicNumber;
    private String contactNumber;
    private UserDTO userDTO;

    public CustomerDTO() {}
    
	public CustomerDTO(String customerName, String address, String nicNumber, String contactNumber) {
		this.customerName = customerName;
		this.address = address;
		this.nicNumber = nicNumber;
		this.contactNumber = contactNumber;
	}

    public CustomerDTO(int customerId, String customerName, String address, String nicNumber, String contactNumber) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.nicNumber = nicNumber;
		this.contactNumber = contactNumber;
	}
    
	public CustomerDTO(int customerId, String customerName, String address, String nicNumber, String contactNumber,
			UserDTO userDTO) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.nicNumber = nicNumber;
		this.contactNumber = contactNumber;
		this.userDTO = userDTO;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

}
