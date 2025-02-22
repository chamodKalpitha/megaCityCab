package com.bms.controller;

import java.sql.SQLException;
import java.util.List;

import com.bms.dao.UserDAO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.model.User;
import com.bms.utils.InputValidator;

public class UserController {
	
    private final UserDAO userDAO;
    
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public Boolean createUser(UserDTO userDto) throws SQLException, IllegalArgumentException {
    	
    	InputValidator.isValidEmail(userDto.getUserEmail());
    	String userPassword;
    	if(userDto.getPassword()==null) {
    		userPassword = "12345678";
    	} else {
    		userPassword = userDto.getPassword();
    		InputValidator.isValidPassword(userPassword);
    	}
    	User user = new User(userDto.getUserName(),userDto.getUserEmail(),AccountStatus.ACTIVE,userDto.getAccountType(),userPassword,false);
    	return userDAO.createUser(user);
    }
    
    public List<UserDTO> getUsers(String search, int limit, int offset) throws SQLException {
    	return userDAO.getUsers(search, limit, offset);
    }
    
    public int getUserCount(String search) throws SQLException {
    	return userDAO.getUsersCount(search);
    }
    
    public boolean deleteUser(int userId) throws SQLException {
    	return userDAO.deleteUser(userId);
    }
    
    public UserDTO getUserById(int userId) throws SQLException {
    	return userDAO.getUserById(userId);
    }
    
    public boolean updateUser(UserDTO userDto) throws SQLException, IllegalArgumentException{
    	InputValidator.isValidEmail(userDto.getUserEmail());
    	User user = new User(userDto.getUserId(),userDto.getUserName(),userDto.getUserEmail(),userDto.getAccountStatus(),userDto.getAccountType());
    	return userDAO.updateUser(user);
    	
    }
}
