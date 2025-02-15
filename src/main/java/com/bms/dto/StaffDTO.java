package com.bms.dto;

import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;

public class StaffDTO {
	
	private String empName;
    private String empEmail;
    private AccountStatus status;
    private AccountType type;
    
	public StaffDTO(String empName, String empEmail, AccountStatus status, AccountType type) {
		super();
		this.empName = empName;
		this.empEmail = empEmail;
		this.status = status;
		this.type = type;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
	
	
    
    
}
