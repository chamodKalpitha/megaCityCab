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

import com.bms.dao.DriverDAO;
import com.bms.dto.DriverDTO;
import com.bms.enums.DriverStatus;
import com.bms.model.Driver;
import com.bms.utils.InputValidator;

@ExtendWith(MockitoExtension.class)
class DriverControllerTest {

    @Mock
    private DriverDAO driverDAO;

    @InjectMocks
    private DriverController driverController;

    private DriverDTO driverDTO;
    private Driver driver;

    @BeforeEach
    void setUp() {
        driverDTO = new DriverDTO(1, "John Doe", "123456789V", "0771234567", DriverStatus.AVAILABLE);
        driver = new Driver();
    }

    @Test
    void testCreateDriver() throws SQLException {
        when(driverDAO.createDriver(any(Driver.class))).thenReturn(true);

        boolean result = driverController.createDriver(driverDTO);

        assertTrue(result);
        verify(driverDAO, times(1)).createDriver(any(Driver.class));
    }

    @Test
    void testGetDrivers() throws SQLException {
        List<DriverDTO> driverList = Arrays.asList(driverDTO);
        when(driverDAO.getDrivers(anyString(), anyInt(), anyInt())).thenReturn(driverList);

        List<DriverDTO> result = driverController.getDrivers("", 10, 0);

        assertEquals(1, result.size());
        verify(driverDAO, times(1)).getDrivers(anyString(), anyInt(), anyInt());
    }

    @Test
    void testDeleteDriver() throws SQLException {
        when(driverDAO.deleteDriver(anyInt())).thenReturn(true);

        boolean result = driverController.deleteDriver(1);

        assertTrue(result);
        verify(driverDAO, times(1)).deleteDriver(anyInt());
    }

    @Test
    void testGetDriverById() throws SQLException {
        when(driverDAO.getDriverById(anyInt())).thenReturn(driverDTO);

        DriverDTO result = driverController.getDriverById(1);

        assertNotNull(result);
        assertEquals("John Doe", result.getDriverName());
        verify(driverDAO, times(1)).getDriverById(anyInt());
    }

    @Test
    void testUpdateDriver() throws SQLException {
        when(driverDAO.updateDriver(any(Driver.class))).thenReturn(true);

        boolean result = driverController.updateDriver(driverDTO);

        assertTrue(result);
        verify(driverDAO, times(1)).updateDriver(any(Driver.class));
    }

    @Test
    void testCheckDuplicateDriver() throws SQLException {
        when(driverDAO.checkDuplicateDriver(anyString(), anyString())).thenReturn(true);

        boolean result = driverController.checkDuplicateDriver("123456789V", "0771234567");

        assertTrue(result);
        verify(driverDAO, times(1)).checkDuplicateDriver(anyString(), anyString());
    }
}

