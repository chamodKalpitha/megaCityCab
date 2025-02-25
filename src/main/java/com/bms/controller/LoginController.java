package com.bms.controller;
import java.sql.SQLException;
import com.bms.dao.LoginDAO;
import com.bms.dto.LoginDTO;
import com.bms.model.User;

public class LoginController {
	
	private final LoginDAO loginDAO;
	
	public LoginController(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public LoginDTO login(LoginDTO loginDTO) throws SQLException {
		User user = new User(loginDTO.getEmail(),loginDTO.getPassword());
		return loginDAO.login(user);
	}
}
