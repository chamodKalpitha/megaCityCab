package com.bms.dto;

import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;

public class UserDTO {
	private int userId;
    private String userEmail;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private String password;
    private boolean isDelete;
      
	public UserDTO(int userId, String userEmail) {
		this.userId = userId;
		this.userEmail = userEmail;
	}

	public UserDTO(String userEmail, String password) {
		this.userEmail = userEmail;
		this.password = password;
	}
	
	
	public UserDTO(String userEmail, AccountType accountType) {
		this.userEmail = userEmail;
		this.accountType = accountType;
	}

	public UserDTO(int userId, String userEmail, AccountType accountType,
			AccountStatus accountStatus) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

}
