package com.bms.dao;

import java.sql.SQLException;

import com.bms.dto.LoginDTO;
import com.bms.model.User;

public interface LoginDAO {
	LoginDTO login(User user) throws SQLException;
}
