package com.bms.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bms.dao.CustomerDAO;
import com.bms.dto.CustomerDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.Customer;
import com.bms.model.User;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerController customerController;

    private UserDTO userDto;
    private CustomerDTO customerDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDTO("test@example.com", "password123");
        customerDto = new CustomerDTO(1, "John Doe", "123 Street", "123456789V", "0771234567", userDto);
    }

    @Test
    void testCreateCustomerSuccess() throws SQLException {
        when(customerDAO.createCustomer(any(User.class), any(Customer.class))).thenReturn(true);
        boolean result = customerController.createCustomer(userDto, customerDto);
        assertTrue(result);
        verify(customerDAO).createCustomer(any(User.class), any(Customer.class));
    }

    @Test
    void testGetCustomers() throws SQLException {
        List<CustomerDTO> customers = Arrays.asList(customerDto);
        when(customerDAO.getCustomers("", 10, 0)).thenReturn(customers);
        List<CustomerDTO> result = customerController.getCustomers("", 10, 0);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
    }

    @Test
    void testGetCustomersCount() throws SQLException {
        when(customerDAO.getCustomersCount(""))
                .thenReturn(5);
        int count = customerController.getCustomersCount("");
        assertEquals(5, count);
    }

    @Test
    void testGetCustomerById() throws SQLException {
        when(customerDAO.getCustomerById(1)).thenReturn(customerDto);
        CustomerDTO result = customerController.getCustomerById(1);
        assertNotNull(result);
        assertEquals("John Doe", result.getCustomerName());
    }

    @Test
    void testUpdateCustomerSuccess() throws SQLException {
        when(customerDAO.updateCustomer(any(Customer.class), any(User.class))).thenReturn(true);
        boolean result = customerController.updateCustomer(customerDto);
        assertTrue(result);
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        when(customerDAO.deleteCustomer(1)).thenReturn(true);
        boolean result = customerController.deleteCustomer(1);
        assertTrue(result);
    }

    @Test
    void testIsEmailDuplicate() throws SQLException {
        when(customerDAO.isEmailDuplicate("test@example.com")).thenReturn(true);
        boolean result = customerController.isEmailDuplicate("test@example.com");
        assertTrue(result);
    }

    @Test
    void testIsNicNumberDuplicate() throws SQLException {
        when(customerDAO.isNicNumberDuplicate("123456789V")).thenReturn(false);
        boolean result = customerController.isNicNumberDuplicate("123456789V");
        assertFalse(result);
    }

    @Test
    void testIsContactNumberDuplicate() throws SQLException {
        when(customerDAO.isContactNumberDuplicate("0771234567")).thenReturn(false);
        boolean result = customerController.isContactNumberDuplicate("0771234567");
        assertFalse(result);
    }
}
