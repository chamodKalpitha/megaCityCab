package com.bms.controller;

import com.bms.dao.LoginDAO;
import com.bms.dto.LoginDTO;
import com.bms.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private LoginDAO loginDAO;

    @InjectMocks
    private LoginController loginController;

    private LoginDTO loginDTO;

    @BeforeEach
    public void setUp() {
        loginDTO = new LoginDTO();
    }

    @Test
    public void testLogin_Failure() throws SQLException {
        // Arrange
        User mockUser = new User("wrong@example.com", "wrongPassword");
        when(loginDAO.login(mockUser)).thenThrow(new SQLException("Invalid credentials"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> loginController.login(new LoginDTO()));
        assertEquals("Invalid credentials", exception.getMessage());
        verify(loginDAO, times(1)).login(mockUser);
    }
}
