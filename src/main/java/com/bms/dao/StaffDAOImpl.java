package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.dto.StaffDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.Staff;
import com.bms.model.User;
import com.bms.utils.PasswordUtils;

public class StaffDAOImpl implements StaffDAO {
	
    private final Connection connection;
    
	public StaffDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
	}

	@Override
    public boolean createStaff(User user, Staff staff) throws SQLException {
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());

        String query1 = "INSERT INTO app_user (user_email, account_status, account_type, password) VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO staff (user_id, name, address, contact_number) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt2 = connection.prepareStatement(query2)) {
            
            connection.setAutoCommit(false); // Start transaction
            
            stmt1.setString(1, user.getUserEmail());
            stmt1.setString(2, user.getAccountStatus().name());
            stmt1.setString(3, user.getAccountType().name());
            stmt1.setString(4, hashedPassword);
            
            stmt1.executeUpdate();
            
            // Retrieve generated user_id
            try (ResultSet generatedKeys = stmt1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    
                    stmt2.setInt(1, userId);
                    stmt2.setString(2, staff.getName());
                    stmt2.setString(3, staff.getAddress());
                    stmt2.setString(4, staff.getContactNumber());
                    
                    stmt2.executeUpdate();
                } else {
                    connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            connection.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            connection.rollback(); // Rollback transaction in case of failure
            throw e;
        } finally {
            connection.setAutoCommit(true); // Restore default behavior
        }
    }

    @Override
    public List<StaffDTO> getStaffs(String search, int limit, int offset) throws SQLException {
        List<StaffDTO> staffList = new ArrayList<>();

        String sql = "SELECT u.*,s.* FROM app_user u " + 
        			 "JOIN staff s ON u.user_id = s.user_Id " + 
        			 "WHERE s.name LIKE ? AND u.is_delete != 1 " + 
        			 "AND u.account_type IN ('STAFF', 'MANAGER') " + 
        			 "ORDER BY u.user_id ASC LIMIT ? OFFSET ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO userDTO = new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("user_email"),
                    AccountType.valueOf(rs.getString("account_type")),
                    AccountStatus.valueOf(rs.getString("account_status"))
                );

                StaffDTO staffDTO = new StaffDTO(
                    rs.getInt("staff_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact_number"),
                    rs.getBoolean("is_delete"),
                    userDTO
                );
                
                staffList.add(staffDTO);
            }
        }
        return staffList;
    }

	@Override
	public int getStaffsCount(String search) throws SQLException {

        String sql = "SELECT COUNT(*) FROM app_user u " + 
   			 "JOIN staff s ON u.user_id = s.user_Id " + 
   			 "WHERE s.name LIKE ? AND u.is_delete != 1 " + 
   			 "AND u.account_type IN ('STAFF', 'MANAGER') " + 
   			 "ORDER BY u.user_id";
        
        
	   try (PreparedStatement ps = connection.prepareStatement(sql)) {
	       String searchParam = "%" + search + "%";
	       ps.setString(1, searchParam);
	       ResultSet rs = ps.executeQuery();
	       while (rs.next()) {
	    	   return rs.getInt(1);
	       }
	   }
		return 0;
	}
	
	@Override
	public boolean deleteStaff(int staffId) throws SQLException {
	    String getUserIdSql = "SELECT user_id FROM staff WHERE staff_id = ?";
	    String updateStaffSql = "UPDATE staff SET is_delete = 1 WHERE staff_id = ?";
	    String updateUserSql = "UPDATE app_user SET is_delete = 1 WHERE user_id = ? AND account_type != 'ADMIN'";

	    try {
	        connection.setAutoCommit(false); // Start transaction

	        int userId = -1;
	        try (PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSql)) {
	            getUserIdStmt.setInt(1, staffId);
	            try (ResultSet rs = getUserIdStmt.executeQuery()) {
	                if (rs.next()) {
	                    userId = rs.getInt("user_id");
	                } else {
	                    connection.rollback(); // Rollback if staff ID not found
	                    return false;
	                }
	            }
	        }

	        try (PreparedStatement updateStaffStmt = connection.prepareStatement(updateStaffSql)) {
	            updateStaffStmt.setInt(1, staffId);
	            int staffUpdated = updateStaffStmt.executeUpdate();
	            if (staffUpdated == 0) {
	                connection.rollback(); // Rollback if no rows affected
	                return false;
	            }
	        }

	        try (PreparedStatement updateUserStmt = connection.prepareStatement(updateUserSql)) {
	            updateUserStmt.setInt(1, userId);
	            int userUpdated = updateUserStmt.executeUpdate();
	            if (userUpdated == 0) {
	                connection.rollback(); // Rollback if no rows affected
	                return false;
	            }
	        }

	        connection.commit(); // Commit if all updates succeed
	        return true;
	    } catch (SQLException e) {
	        connection.rollback(); // Rollback on error
	        e.printStackTrace();
	        return false;
	    } finally {
	        connection.setAutoCommit(true); // Restore default behavior
	    }
	}
	
    @Override
    public StaffDTO getStaffById(int userId) throws SQLException {
        String sql = "SELECT u.user_id, u.user_email, u.account_type, u.account_status, " +
                     "s.staff_id, s.name, s.address, s.contact_number, s.is_delete " +
                     "FROM app_user u " +
                     "JOIN staff s ON u.user_id = s.user_id " +
                     "WHERE s.staff_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDTO userDTO = new UserDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_email"),
                        AccountType.valueOf(rs.getString("account_type")),
                        AccountStatus.valueOf(rs.getString("account_status"))
                    );
                    
                    return new StaffDTO(
                        rs.getInt("staff_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact_number"),
                        rs.getBoolean("is_delete"),
                        userDTO
                    );
                }
            }
        }
        
        return null;
    }
    
    @Override
    public boolean updateStaff(User user, Staff staff) throws SQLException {
        String query1 = "UPDATE app_user SET user_email = ?, account_status = ?, account_type = ? WHERE user_id = ?";
        String query2 = "UPDATE staff SET name = ?, address = ?, contact_number = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt1 = connection.prepareStatement(query1);
             PreparedStatement stmt2 = connection.prepareStatement(query2)) {
            
            connection.setAutoCommit(false);
            
            stmt1.setString(1, user.getUserEmail());
            stmt1.setString(2, user.getAccountStatus().name());
            stmt1.setString(3, user.getAccountType().name());
            stmt1.setInt(4, user.getUserId());
            
            int rowsUpdated1 = stmt1.executeUpdate();
            
            stmt2.setString(1, staff.getName());
            stmt2.setString(2, staff.getAddress());
            stmt2.setString(3, staff.getContactNumber());
            stmt2.setInt(4, user.getUserId());
            
            int rowsUpdated2 = stmt2.executeUpdate();
            
            if (rowsUpdated1 > 0 && rowsUpdated2 > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true); 
        }
    }

	@Override
	public boolean isEmailDuplicate(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM app_user WHERE user_email = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
	}

	@Override
	public boolean isContactNumberDuplicate(String contactNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM staff WHERE contact_number = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contactNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
	}

	@Override
	public boolean updateStaffProfile(User user, Staff staff) throws SQLException {
        String query1 = "UPDATE app_user SET user_email = ? WHERE user_id = ?";
        String query2 = "UPDATE staff SET name = ?, address = ?, contact_number = ? WHERE user_id = ?";
        
        
        try (PreparedStatement stmt1 = connection.prepareStatement(query1);
             PreparedStatement stmt2 = connection.prepareStatement(query2)) {
            
            connection.setAutoCommit(false);
            
            stmt1.setString(1, user.getUserEmail());
            stmt1.setInt(2, user.getUserId());
            
            int rowsUpdated1 = stmt1.executeUpdate();
            
            stmt2.setString(1, staff.getName());
            stmt2.setString(2, staff.getAddress());
            stmt2.setString(3, staff.getContactNumber());
            stmt2.setInt(4, user.getUserId());
            
            int rowsUpdated2 = stmt2.executeUpdate();
            
            if (rowsUpdated1 > 0 && rowsUpdated2 > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true); 
        }
	}

	@Override
	public StaffDTO getStaffByUserId(int userId) throws SQLException {
	        String sql = "SELECT u.user_id, u.user_email, u.account_type, u.account_status, " +
	                "s.staff_id, s.name, s.address, s.contact_number, s.is_delete " +
	                "FROM app_user u " +
	                "JOIN staff s ON u.user_id = s.user_id " +
	                "WHERE s.user_id = ?";
	   
		   try (PreparedStatement ps = connection.prepareStatement(sql)) {
		       ps.setInt(1, userId);
		       
		       try (ResultSet rs = ps.executeQuery()) {
		           if (rs.next()) {
		               UserDTO userDTO = new UserDTO(
		                   rs.getInt("user_id"),
		                   rs.getString("user_email"),
		                   AccountType.valueOf(rs.getString("account_type")),
		                   AccountStatus.valueOf(rs.getString("account_status"))
		               );
		               
		               return new StaffDTO(
		                   rs.getInt("staff_id"),
		                   rs.getString("name"),
		                   rs.getString("address"),
		                   rs.getString("contact_number"),
		                   rs.getBoolean("is_delete"),
		                   userDTO
		               );
		           }
		       }
		   }
		   
		   return null;
	}

}
