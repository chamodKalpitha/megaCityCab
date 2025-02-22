package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.User;
import com.bms.utils.PasswordUtils;

public class UserDAOImpl implements UserDAO {
	
    private final Connection connection;
    
	public UserDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    	System.out.println(connection);

	}

	@Override
	public boolean createUser(User user) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());

		String qeury = "INSERT INTO app_user (user_name, user_email, account_status, account_type, password) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(qeury, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserEmail());
            stmt.setString(3, user.getAccountStatus().name());
            stmt.setString(4, user.getAccountType().name());
            stmt.setString(5, hashedPassword);
            
            stmt.executeUpdate();
            return true;
        }
	}

	@Override
	public List<UserDTO> getUsers(String search, int limit, int offset) throws SQLException {
		
		List<UserDTO> userList = new ArrayList<>();
        String sql = "SELECT user_id, user_name, user_email, account_type, account_status FROM app_user WHERE (LOWER(user_name) LIKE LOWER(?) "+
                     "OR LOWER(user_name) LIKE LOWER(?) OR LOWER(user_id) LIKE LOWER(?)) AND is_delete != 1 AND account_type != 'ADMIN' ORDER BY user_id ASC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setString(2, searchParam);
            ps.setString(3, searchParam);
            ps.setInt(4, limit);
            ps.setInt(5, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userList.add(new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("user_email"),
                    AccountType.valueOf(rs.getString("account_type").toString()),
                    AccountStatus.valueOf(rs.getString("account_status").toString())
                ));
            }
        }
		return userList;
	}

	@Override
	public int getUsersCount(String search) throws SQLException {
		String sql = "SELECT COUNT(*) FROM app_user WHERE (LOWER(user_name) LIKE LOWER(?) " +
	             "OR LOWER(user_name) LIKE LOWER(?) OR LOWER(user_id) LIKE LOWER(?)) " +
	             "AND is_delete != 1 AND account_type != 'ADMIN' ORDER BY user_id ASC";

	   try (PreparedStatement ps = connection.prepareStatement(sql)) {
	       String searchParam = "%" + search + "%";
	       ps.setString(1, searchParam);
	       ps.setString(2, searchParam);
	       ps.setString(3, searchParam);
	
	       ResultSet rs = ps.executeQuery();
	       while (rs.next()) {
	    	   return rs.getInt(1);
	       }
	   }
		return 0;
	}
	
	

	@Override
	public boolean deleteUser(int userId) throws SQLException {
	    String sql = "UPDATE app_user SET is_delete = 1 WHERE user_id = ? AND account_type != 'ADMIN'";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        int rowsAffected = ps.executeUpdate();  
	        return rowsAffected > 0; 
	    }
	}

	@Override
	public UserDTO getUserById(int userId) throws SQLException {
	    String sql = "SELECT user_id, user_name, user_email, account_type, account_status FROM app_user WHERE user_id = ?";
	    
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) { 
	                return new UserDTO(
	                    rs.getInt("user_id"),
	                    rs.getString("user_name"),
	                    rs.getString("user_email"),
	                    AccountType.valueOf(rs.getString("account_type")),
	                    AccountStatus.valueOf(rs.getString("account_status"))
	                );
	            }
	        }
	    }
	    
	    return null;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {
		String query = "UPDATE app_user SET user_name = ?, user_email = ?, account_status = ?, account_type = ? WHERE user_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, user.getUserName());
	        stmt.setString(2, user.getUserEmail());
	        stmt.setString(3, user.getAccountStatus().name());
	        stmt.setString(4, user.getAccountType().name());
	        stmt.setInt(5, user.getUserId());
	        
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    }
	}

	

}
