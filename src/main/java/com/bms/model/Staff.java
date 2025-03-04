package com.bms.model;

public class Staff {
	private int staffId;
	private String name;
	private String address;
	private String contactNumber;
	private int userId;
	private boolean isDelete;
	
	public Staff() {
	}
	
	public Staff(String name, String address, String contactNumber) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
	}

	public Staff(String name, String address, String contactNumber, int userId) {
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.userId = userId;
	}

	public int getStaffId() {
		return staffId;
	}
	
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public boolean isDelete() {
		return isDelete;
	}
	
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
