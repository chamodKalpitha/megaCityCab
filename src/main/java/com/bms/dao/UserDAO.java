package com.bms.dao;

import java.sql.SQLException;
import java.util.List;

import com.bms.dto.UserDTO;
import com.bms.model.User;

public interface UserDAO {
	public boolean createUser(User user) throws SQLException;
	public List<UserDTO> getUsers(String search, int limit, int offset) throws SQLException;
	public int getUsersCount(String search) throws SQLException;
	public boolean deleteUser(int userId) throws SQLException;
	public UserDTO getUserById(int userId) throws SQLException;
	public boolean updateUser(User user) throws SQLException;
}
