package com.bms.dao;

import com.bms.config.DatabaseConfig;
import com.bms.dto.BookingDTO;
import com.bms.dto.CustomerDTO;
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
    public boolean createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (customer_id, booked_vehicle_id, driver_id, booking_date, booking_status, pricing_type, is_delete) VALUES (?, ?, ?, ?, ?, ?, false)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getBookedVehicleId());
            if (booking.getDriverId() != null) {
                stmt.setInt(3, booking.getDriverId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setDate(4, new java.sql.Date(booking.getBookingDate().getTime())); // Convert java.util.Date to java.sql.Date
            stmt.setString(5, booking.getBookingStatus().name());
            stmt.setString(6, booking.getPricingType().name());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<BookingDTO> getBookings(String search, int limit, int offset) throws SQLException {
        List<BookingDTO> bookings = new ArrayList<>();
        String sql = "SELECT b.*, v.*, c.* FROM booking b " +
        			 "JOIN vehicle v ON b.booked_vehicle_id = v.vehicle_id "+
        			 "JOIN customer c ON b.customer_id = c.customer_id "+
        			 "WHERE b.is_delete = 0 "+
        			 "AND c.customer_name LIKE ? LIMIT ? OFFSET ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%"); // Ensure search is properly formatted
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
            	
            	CustomerDTO customer = new CustomerDTO(
                        rs.getInt("customer_id"),
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
                        rs.getInt("customer_id"),
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
        String sql = "SELECT * FROM booking WHERE booking_id = ? AND is_delete = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BookingDTO(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("booked_vehicle_id"),
                        rs.getObject("driver_id") != null ? rs.getInt("driver_id") : null,
                        rs.getDate("booking_date"),
                        BookingStatus.valueOf(rs.getString("booking_status")),
                        PricingType.valueOf(rs.getString("pricing_type"))
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
            if (booking.getDriverId() != null) {
                stmt.setInt(3, booking.getDriverId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
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
