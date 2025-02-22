package com.bms.controller;

import java.sql.SQLException;
import java.util.List;

import com.bms.dao.CustomerDAO;
import com.bms.dto.CustomerDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.Customer;
import com.bms.model.User;
import com.bms.utils.InputValidator;

public class CustomerController {

    private final CustomerDAO customerDAO;

    public CustomerController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public boolean createCustomer(UserDTO userDto, CustomerDTO customerDto) throws SQLException, IllegalArgumentException {
    	User user = new User(customerDto.getCustomerName(),userDto.getUserEmail(),AccountStatus.ACTIVE,AccountType.CUSTOMER,userDto.getPassword(),false);
        Customer customer = new Customer(customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getNicNumber(),customerDto.getContactNumber());
        return customerDAO.createCustomer(user, customer);
    }

    public List<CustomerDTO> getCustomers(String search, int limit, int offset) throws SQLException {
        return customerDAO.getCustomers(search, limit, offset);
    }

    public int getCustomersCount(String search) throws SQLException {
        return customerDAO.getCustomersCount(search);
    }

    public CustomerDTO getCustomerById(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId);
    }

    public boolean updateCustomer(CustomerDTO customerDto) throws SQLException, IllegalArgumentException {
        InputValidator.isValidPhoneNumber(customerDto.getContactNumber());
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setAddress(customerDto.getAddress());
        customer.setNicNumber(customerDto.getNicNumber());
        customer.setContactNumber(customerDto.getContactNumber());
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int customerId) throws SQLException {
        return customerDAO.deleteCustomer(customerId);
    }
    
    public boolean isEmailDuplicate(String email) throws SQLException {
    	return customerDAO.isEmailDuplicate(email);
    }
    
    public boolean isNicNumberDuplicate(String nicNumber) throws SQLException {
    	return customerDAO.isNicNumberDuplicate(nicNumber);
    }
    
    public boolean isContactNumberDuplicate(String contactNumber) throws SQLException {
    	return customerDAO.isContactNumberDuplicate(contactNumber);
    }
}
