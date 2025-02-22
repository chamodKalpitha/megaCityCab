package com.bms.model;

import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private AccountStatus accountStatus;
    private AccountType accountType;
    private String password;
    private boolean isDelete;
    
	public User() {
	}
	
	public User(String userEmail, String password) {
		super();
		this.userEmail = userEmail;
		this.password = password;
	}
	
	public User(String userName, String userEmail, String password) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
	}

	public User(int userId, String userName, String userEmail, AccountStatus accountStatus, AccountType accountType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
	}

	public User(String userName, String userEmail, AccountStatus accountStatus, AccountType accountType,
			String password, boolean isDelete) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
		this.password = password;
		this.isDelete = isDelete;
	}
	

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
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
