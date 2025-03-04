package com.bms.dao;

import com.bms.config.DatabaseConfig;
import com.bms.dto.CustomerDTO;
import com.bms.model.Customer;
import com.bms.model.User;
import com.bms.utils.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private final Connection connection;
    
	public CustomerDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
	}


    public boolean createCustomer(User user, Customer customer) throws SQLException {
        String userSql = "INSERT INTO app_user (user_name, user_email, account_status, account_type, password) VALUES (?, ?, ?, ?, ?)";
        String customerSql = "INSERT INTO customer (customer_name, address, nic_number, contact_number, user_id, is_delete) VALUES (?, ?, ?, ?, ?, false)";
        
        try {
            connection.setAutoCommit(false);
            
            try (PreparedStatement userStmt = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
                userStmt.setString(1, user.getUserEmail());
                userStmt.setString(2, user.getAccountStatus().name());
                userStmt.setString(3, user.getAccountType().name());
                userStmt.setString(4, hashedPassword);
                
                int affectedRows = userStmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
                
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        
                        try (PreparedStatement customerStmt = connection.prepareStatement(customerSql)) {
                            customerStmt.setString(1, customer.getCustomerName());
                            customerStmt.setString(2, customer.getAddress());
                            customerStmt.setString(3, customer.getNicNumber());
                            customerStmt.setString(4, customer.getContactNumber());
                            customerStmt.setInt(5, userId);
                            
                            if (customerStmt.executeUpdate() > 0) {
                                connection.commit();
                                return true;
                            } else {
                                connection.rollback();
                                return false;
                            }
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    @Override
    public List<CustomerDTO> getCustomers(String search, int limit, int offset) throws SQLException {
        List<CustomerDTO> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE is_delete = false AND (customer_name LIKE ? OR nic_number LIKE ?) LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(new CustomerDTO(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("nic_number"),
                        rs.getString("contact_number")
                ));
            }
        }
        return customers;
    }
    
    @Override
    public int getCustomersCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM customers WHERE is_delete = false AND (customer_name LIKE ? OR nic_number LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    @Override
    public CustomerDTO getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CustomerDTO(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("nic_number"),
                        rs.getString("contact_number")
                );
            }
        }
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET customer_name = ?, address = ?, nic_number = ?, contact_number = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getNicNumber());
            stmt.setString(4, customer.getContactNumber());
            stmt.setInt(5, customer.getCustomerId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) throws SQLException {
        String sqlCustomer = "UPDATE customers SET is_delete = true WHERE customer_id = ?";
        String sqlUser = "UPDATE users SET is_delete = true WHERE user_id = (SELECT user_id FROM customers WHERE customer_id = ?)";

        try (PreparedStatement stmtCustomer = connection.prepareStatement(sqlCustomer);
             PreparedStatement stmtUser = connection.prepareStatement(sqlUser)) {

            connection.setAutoCommit(false); 

            stmtCustomer.setInt(1, customerId);
            int customerUpdated = stmtCustomer.executeUpdate();

            stmtUser.setInt(1, customerId);
            int userUpdated = stmtUser.executeUpdate();

            connection.commit();
            return customerUpdated > 0 && userUpdated > 0;
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
    public boolean isNicNumberDuplicate(String nicNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE nic_number = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nicNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean isContactNumberDuplicate(String contactNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE contact_number = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contactNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    

}
