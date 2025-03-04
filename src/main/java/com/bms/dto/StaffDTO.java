package com.bms.dto;

public class StaffDTO {
	private int staffId;
	private String name;
	private String address;
	private String contactNumber;
	private Boolean isDelete;
	private UserDTO userDTO;
	
	
	
	public StaffDTO(String name, String address, String contactNumber, UserDTO userDTO) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.userDTO = userDTO;
	}


	public StaffDTO(String name, String address, String contactNumber, Boolean isDelete, UserDTO userDTO) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.isDelete = isDelete;
		this.userDTO = userDTO;
	}
	
	
	public StaffDTO(int staffId, String name, String address, String contactNumber, Boolean isDelete, UserDTO userDTO) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.isDelete = isDelete;
		this.userDTO = userDTO;
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
	
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	public UserDTO getUserDTO() {
		return userDTO;
	}
	
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
}
