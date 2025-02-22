package com.bms.dao;

import com.bms.dto.CustomerDTO;
import com.bms.model.Customer;
import com.bms.model.User;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    public boolean createCustomer(User user, Customer customer) throws SQLException;
    public List<CustomerDTO> getCustomers(String search, int limit, int offset) throws SQLException;
    public CustomerDTO getCustomerById(int customerId) throws SQLException;
    public boolean updateCustomer(Customer customer) throws SQLException;
    public boolean deleteCustomer(int customerId) throws SQLException;
    public int getCustomersCount(String search)throws SQLException;
    public boolean isEmailDuplicate(String email) throws SQLException;
    public boolean isNicNumberDuplicate(String nicNumber) throws SQLException;
    public boolean isContactNumberDuplicate(String contactNumber) throws SQLException;
}
