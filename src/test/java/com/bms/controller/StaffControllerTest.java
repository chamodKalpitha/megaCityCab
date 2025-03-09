package com.bms.controller;

import com.bms.dao.StaffDAO;
import com.bms.dto.StaffDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.model.Staff;
import com.bms.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffControllerTest {

    @Mock
    private StaffDAO staffDAO;

    @InjectMocks
    private StaffController staffController;

    private StaffDTO staffDTO;

    @BeforeEach
    public void setUp() {
        staffDTO = new StaffDTO(
            "John Doe", 
            "123 Street", 
            "1234567890", 
            new UserDTO("johndoe@example.com", AccountType.CUSTOMER)
        );
    }

    @Test
    public void testGetStaffs() throws SQLException {
        // Arrange
        List<StaffDTO> mockStaffList = List.of(staffDTO);
        when(staffDAO.getStaffs("John", 10, 0)).thenReturn(mockStaffList);

        // Act
        List<StaffDTO> result = staffController.getStaffs("John", 10, 0);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(staffDAO, times(1)).getStaffs("John", 10, 0);
    }

    @Test
    public void testDeleteStaff_Success() throws SQLException {
        // Arrange
        when(staffDAO.deleteStaff(1)).thenReturn(true);

        // Act
        boolean result = staffController.deleteStaff(1);

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).deleteStaff(1);
    }

    @Test
    public void testDeleteStaff_Failure() throws SQLException {
        // Arrange
        when(staffDAO.deleteStaff(1)).thenReturn(false);

        // Act
        boolean result = staffController.deleteStaff(1);

        // Assert
        assertFalse(result);
        verify(staffDAO, times(1)).deleteStaff(1);
    }

    @Test
    public void testGetStaffById() throws SQLException {
        // Arrange
        when(staffDAO.getStaffById(1)).thenReturn(staffDTO);

        // Act
        StaffDTO result = staffController.getStaffById(1);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(staffDAO, times(1)).getStaffById(1);
    }


    @Test
    public void testIsEmailDuplicate() throws SQLException {
        // Arrange
        when(staffDAO.isEmailDuplicate("johndoe@example.com")).thenReturn(true);

        // Act
        boolean result = staffController.isEmailDuplicate("johndoe@example.com");

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).isEmailDuplicate("johndoe@example.com");
    }

    @Test
    public void testIsContactNumberDuplicate() throws SQLException {
        // Arrange
        when(staffDAO.isContactNumberDuplicate("1234567890")).thenReturn(true);

        // Act
        boolean result = staffController.isContactNumberDuplicate("1234567890");

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).isContactNumberDuplicate("1234567890");
    }


    @Test
    public void testGetStaffByUserId() throws SQLException {
        // Arrange
        when(staffDAO.getStaffByUserId(1)).thenReturn(staffDTO);

        // Act
        StaffDTO result = staffController.getStaffByUserId(1);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(staffDAO, times(1)).getStaffByUserId(1);
    }
}