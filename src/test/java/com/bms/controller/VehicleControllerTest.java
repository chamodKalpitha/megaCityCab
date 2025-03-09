package com.bms.controller;

import com.bms.dao.VehicleDAO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;
import com.bms.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private VehicleController vehicleController;

    private VehicleDTO vehicleDTO;

    @BeforeEach
    public void setUp() {
        vehicleDTO = new VehicleDTO(
                "Toyota",
                "Corolla",
                "XYZ123",
                5,
                VehicleStatus.AVAILABLE,
                VehicleType.CAR,
                "http://image.url",
                10.0,
                100.0
        );
    }

    @Test
    public void testGetVehicles() throws SQLException {
        // Arrange
        List<VehicleDTO> mockVehicles = List.of(vehicleDTO);
        when(vehicleDAO.getVehicles("Toyota", 10, 0)).thenReturn(mockVehicles);

        // Act
        List<VehicleDTO> result = vehicleController.getVehicles("Toyota", 10, 0);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getVehicleBrand());
        verify(vehicleDAO, times(1)).getVehicles("Toyota", 10, 0);
    }

    @Test
    public void testGetVehicleCount() throws SQLException {
        // Arrange
        when(vehicleDAO.getVehiclesCount("Toyota")).thenReturn(1);

        // Act
        int result = vehicleController.getVehicleCount("Toyota");

        // Assert
        assertEquals(1, result);
        verify(vehicleDAO, times(1)).getVehiclesCount("Toyota");
    }


    @Test
    public void testDeleteVehicle_Success() throws SQLException {
        // Arrange
        when(vehicleDAO.deleteVehicle(1)).thenReturn(true);

        // Act
        boolean result = vehicleController.deleteVehicle(1);

        // Assert
        assertTrue(result);
        verify(vehicleDAO, times(1)).deleteVehicle(1);
    }

    @Test
    public void testDeleteVehicle_Failure() throws SQLException {
        // Arrange
        when(vehicleDAO.deleteVehicle(1)).thenReturn(false);

        // Act
        boolean result = vehicleController.deleteVehicle(1);

        // Assert
        assertFalse(result);
        verify(vehicleDAO, times(1)).deleteVehicle(1);
    }

    @Test
    public void testGetVehiclesNumberPlate() throws SQLException {
        // Arrange
        List<VehicleDTO> mockVehicles = List.of(vehicleDTO);
        when(vehicleDAO.getVehiclesNumberPlate()).thenReturn(mockVehicles);

        // Act
        List<VehicleDTO> result = vehicleController.getVehiclesNumberPlate();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("XYZ123", result.get(0).getPlateNumber());
        verify(vehicleDAO, times(1)).getVehiclesNumberPlate();
    }

    @Test
    public void testCheckVehicleAvailable_Success() throws SQLException {
        // Arrange
        Date bookingDate = new Date();
        when(vehicleDAO.checkVehicleAvailable(1, bookingDate)).thenReturn(true);

        // Act
        boolean result = vehicleController.checkVehicleAvailable(1, bookingDate);

        // Assert
        assertTrue(result);
        verify(vehicleDAO, times(1)).checkVehicleAvailable(1, bookingDate);
    }

    @Test
    public void testCheckVehicleAvailable_Failure() throws SQLException {
        // Arrange
        Date bookingDate = new Date();
        when(vehicleDAO.checkVehicleAvailable(1, bookingDate)).thenReturn(false);

        // Act
        boolean result = vehicleController.checkVehicleAvailable(1, bookingDate);

        // Assert
        assertFalse(result);
        verify(vehicleDAO, times(1)).checkVehicleAvailable(1, bookingDate);
    }

    @Test
    public void testCheckDuplicateVehicleNumberPlate_Success() throws SQLException {
        // Arrange
        when(vehicleDAO.checkDuplicateVehicleNumberPlate("XYZ123")).thenReturn(true);

        // Act
        boolean result = vehicleController.checkDuplicateVehicleNumberPlate("XYZ123");

        // Assert
        assertTrue(result);
        verify(vehicleDAO, times(1)).checkDuplicateVehicleNumberPlate("XYZ123");
    }

    @Test
    public void testCheckDuplicateVehicleNumberPlate_Failure() throws SQLException {
        // Arrange
        when(vehicleDAO.checkDuplicateVehicleNumberPlate("XYZ123")).thenReturn(false);

        // Act
        boolean result = vehicleController.checkDuplicateVehicleNumberPlate("XYZ123");

        // Assert
        assertFalse(result);
        verify(vehicleDAO, times(1)).checkDuplicateVehicleNumberPlate("XYZ123");
    }
}
