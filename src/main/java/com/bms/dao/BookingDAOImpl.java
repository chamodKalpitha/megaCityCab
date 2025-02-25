package com.bms.dao;

import com.bms.config.DatabaseConfig;
import com.bms.dto.BookingDTO;
import com.bms.dto.CustomerDTO;
import com.bms.dto.UserDTO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;
import com.bms.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private final Connection connection;
    
	public BookingDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
	}

	@Override
	public BookingDTO createBooking(Booking booking) throws SQLException {
	    String bookingSql = "INSERT INTO booking (user_id, booked_vehicle_id, driver_id, booking_date, booking_status, pricing_type, is_delete) VALUES (?, ?, null, ?, ?, ?, false)";
	    String vehicleUpdateSql = "UPDATE vehicle SET vehicle_status = ? WHERE vehicle_id = ?";
	    String getBookingSql = "SELECT b.*, v.*, c.* FROM booking b " +
	                           "JOIN vehicle v ON b.booked_vehicle_id = v.vehicle_id " +
	                           "JOIN customer c ON b.user_id = c.user_id " +
	                           "WHERE b.booking_id = ?";

	    PreparedStatement bookingStmt = null;
	    PreparedStatement vehicleUpdateStmt = null;
	    PreparedStatement getBookingStmt = null;
	    ResultSet rs = null;

	    try {
	        connection.setAutoCommit(false);

	        // Insert booking
	        bookingStmt = connection.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
	        bookingStmt.setInt(1, booking.getUserId());
	        bookingStmt.setInt(2, booking.getBookedVehicleId());
	        bookingStmt.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
	        bookingStmt.setString(4, booking.getBookingStatus().name());
	        bookingStmt.setString(5, booking.getPricingType().name());

	        int bookingRowsAffected = bookingStmt.executeUpdate();

	        // Retrieve generated booking_id
	        int bookingId = -1;
	        rs = bookingStmt.getGeneratedKeys();
	        if (rs.next()) {
	            bookingId = rs.getInt(1);
	        }

	        // Update vehicle status
	        vehicleUpdateStmt = connection.prepareStatement(vehicleUpdateSql);
	        vehicleUpdateStmt.setString(1, VehicleStatus.INUSE.name());
	        vehicleUpdateStmt.setInt(2, booking.getBookedVehicleId());

	        int vehicleRowsAffected = vehicleUpdateStmt.executeUpdate();

	        // Check if both operations were successful
	        if (bookingRowsAffected > 0 && vehicleRowsAffected > 0 && bookingId != -1) {
	            connection.commit();

	            // Retrieve and return the created booking
	            getBookingStmt = connection.prepareStatement(getBookingSql);
	            getBookingStmt.setInt(1, bookingId);
	            rs = getBookingStmt.executeQuery();

	            if (rs.next()) {
	                return new BookingDTO(
	                        rs.getInt("booking_id")
	                );
	            }
	        }

	        connection.rollback();
	        return null;

	    } catch (SQLException e) {
	        connection.rollback();
	        throw e;
	    } finally {
	        connection.setAutoCommit(true);
	        if (rs != null) rs.close();
	        if (bookingStmt != null) bookingStmt.close();
	        if (vehicleUpdateStmt != null) vehicleUpdateStmt.close();
	        if (getBookingStmt != null) getBookingStmt.close();
	    }
	}


    @Override
    public List<BookingDTO> getBookings(String search, int limit, int offset) throws SQLException {
        List<BookingDTO> bookings = new ArrayList<>();
        String sql = "SELECT b.*, v.*, c.* FROM booking b " +
        			 "JOIN vehicle v ON b.booked_vehicle_id = v.vehicle_id "+
        			 "JOIN customer c ON b.user_id = c.user_id "+
        			 "WHERE b.is_delete = 0 "+
        			 "AND c.customer_name LIKE ? LIMIT ? OFFSET ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%"); // Ensure search is properly formatted
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	
            	CustomerDTO customer = new CustomerDTO(
                        rs.getInt("user_id"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("nic_number"),
                        rs.getString("contact_number")
                );
            	
            	
            	VehicleDTO vehicle = new VehicleDTO(
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_brand"),
                        rs.getString("vehicle_model"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        VehicleStatus.valueOf(rs.getString("vehicle_status")),
                        VehicleType.valueOf(rs.getString("vehicle_type")),
                        rs.getString("image_url_string"),
                        rs.getDouble("rate_per_km"),
                        rs.getDouble("rate_per_day")
                    );
            	
            	
            	
                bookings.add(new BookingDTO(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booked_vehicle_id"),
                        rs.getObject("driver_id") != null ? rs.getInt("driver_id") : null,
                        rs.getDate("booking_date"), // Retrieves as java.sql.Date
                        BookingStatus.valueOf(rs.getString("booking_status")),
                        PricingType.valueOf(rs.getString("pricing_type")),
                        vehicle,
                        customer
                ));
            }
        }
        return bookings;
    }


    @Override
    public int getBookingsCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM booking WHERE is_delete = false AND DATE_FORMAT(booking_date, '%Y-%m-%d') LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public BookingDTO getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT b.*, v.*,c.*, a.user_id, a.user_email FROM booking b " +
                     "JOIN vehicle v ON b.booked_vehicle_id = v.vehicle_id " +
                     "JOIN customer c ON b.user_id = c.user_id " +
                     "JOIN app_user a on b.user_id = a.user_id " +
                     "WHERE b.booking_id = ? AND b.is_delete = false";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CustomerDTO customer = new CustomerDTO(
                        rs.getInt("user_id"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("nic_number"),
                        rs.getString("contact_number")
                );

                VehicleDTO vehicle = new VehicleDTO(
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_brand"),
                        rs.getString("vehicle_model"),
                        rs.getString("plate_number"),
                        rs.getInt("capacity"),
                        VehicleStatus.valueOf(rs.getString("vehicle_status")),
                        VehicleType.valueOf(rs.getString("vehicle_type")),
                        rs.getString("image_url_string"),
                        rs.getDouble("rate_per_km"),
                        rs.getDouble("rate_per_day")
                );
                
                UserDTO userDTO = new UserDTO(rs.getInt("user_id"), rs.getString("user_email"));

                return new BookingDTO(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booked_vehicle_id"),
                        rs.getObject("driver_id") != null ? rs.getInt("driver_id") : null,
                        rs.getDate("booking_date"),
                        BookingStatus.valueOf(rs.getString("booking_status")),
                        PricingType.valueOf(rs.getString("pricing_type")),
                        vehicle,
                        customer,
                        userDTO
                );
            }
        }
        return null;
    }

    @Override
    public boolean updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE booking SET booked_vehicle_id = ?, booking_status = ?,driver_id = ?, pricing_type = ?, booking_date = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	stmt.setInt(1, booking.getBookedVehicleId());
            stmt.setString(2, booking.getBookingStatus().name());
            stmt.setInt(3, booking.getDriverId());
            stmt.setString(4, booking.getPricingType().name());
            stmt.setDate(5, new java.sql.Date(booking.getBookingDate().getTime())); // Convert java.util.Date to java.sql.Date
            stmt.setInt(6, booking.getBookingId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) throws SQLException {
        String sql = "UPDATE booking SET is_delete = true WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        }
    }

}
