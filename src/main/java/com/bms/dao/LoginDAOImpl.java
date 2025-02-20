package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bms.config.DatabaseConfig;

import com.bms.dto.LoginDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.User;
import com.bms.utils.PasswordUtils;

public class LoginDAOImpl implements LoginDAO {
	
	private final Connection connection;

    public LoginDAOImpl() {
    	this.connection = DatabaseConfig.getInstance().getConnection();
    	System.out.println(connection);
    }

    @Override
    public LoginDTO login(User user) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());


        String sql = "SELECT account_type FROM app_user WHERE user_email=? AND password=? AND account_status=? AND is_delete=0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setString(1, user.getUserEmail());
            stmt.setString(2, hashedPassword);  
            stmt.setString(3, AccountStatus.ACTIVE.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoginDTO loginDTO = new LoginDTO();
                    loginDTO.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                    return loginDTO;
                }
            }
        } 
        return null; 
    }


}
