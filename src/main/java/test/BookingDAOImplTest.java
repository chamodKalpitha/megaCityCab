package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.Date;

import com.bms.config.DatabaseConfig;
import com.bms.dao.BookingDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class BookingDAOImplTest {
	  private Connection mockConnection;
	    private PreparedStatement mockPreparedStatement;
	    private ResultSet mockResultSet;
	    private BookingDAOImpl bookingDAO;

	    @BeforeEach
	    void setUp() throws SQLException {
	        mockConnection = Mockito.mock(Connection.class);
	        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
	        mockResultSet = Mockito.mock(ResultSet.class);

	        // Mock static DatabaseConfig to return mock connection
	        try (MockedStatic<DatabaseConfig> mockedDatabaseConfig = Mockito.mockStatic(DatabaseConfig.class)) {
	            DatabaseConfig mockDatabaseConfig = Mockito.mock(DatabaseConfig.class);
	            mockedDatabaseConfig.when(DatabaseConfig::getInstance).thenReturn(mockDatabaseConfig);
	            when(mockDatabaseConfig.getConnection()).thenReturn(mockConnection);
	            
	            bookingDAO = new BookingDAOImpl();
	        }

	        // Default mock behavior for createBooking
	        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
	        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
	        when(mockResultSet.next()).thenReturn(true);
	        when(mockResultSet.getInt(1)).thenReturn(1);
	    }

	    @Test
	    void createBooking_Success() throws SQLException {
	        // Arrange
	        Booking booking = new Booking();
	        booking.setUserId(1);
	        booking.setBookedVehicleId(2);
	        booking.setBookingDate(new Date());
	        booking.setBookingStatus(BookingStatus.PENDING);
	        booking.setPricingType(PricingType.PER_DAY_WITH_DRIVER);

	        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

	        // Act
	        BookingDTO result = bookingDAO.createBooking(booking);

	        // Assert
	        assertNotNull(result);
	        assertEquals(1, result.getBookingId());
	        verify(mockConnection).commit();
	    }

	    @Test
	    void createBooking_RollbackOnFailure() throws SQLException {
	        // Arrange
	        Booking booking = new Booking();
	        booking.setUserId(1);
	        booking.setBookedVehicleId(2);
	        booking.setBookingDate(new Date());

	        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("DB error"));

	        // Act & Assert
	        assertThrows(SQLException.class, () -> bookingDAO.createBooking(booking));
	        verify(mockConnection).rollback();
	    }
}
