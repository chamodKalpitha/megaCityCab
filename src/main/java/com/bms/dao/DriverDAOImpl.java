package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.dto.DriverDTO;
import com.bms.enums.DriverStatus;
import com.bms.model.Driver;

public class DriverDAOImpl implements DriverDAO {

    private final Connection connection;

    public DriverDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean createDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO driver (driver_name, nic_number, contact_number, driver_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, driver.getDriverName());
            stmt.setString(2, driver.getNicNumber());
            stmt.setString(3, driver.getContactNumber());
            stmt.setString(4, driver.getDriverStatus().name());
            stmt.executeUpdate();
            return true;
        }
    }

    @Override
    public List<DriverDTO> getDrivers(String search, int limit, int offset) throws SQLException {
        List<DriverDTO> driverList = new ArrayList<>();
        String sql = "SELECT driver_id, driver_name, nic_number, contact_number, driver_status FROM driver WHERE (LOWER(driver_name) LIKE LOWER(?) OR LOWER(nic_number) LIKE LOWER(?)) AND is_delete != 1 ORDER BY driver_id ASC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setString(2, searchParam);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                driverList.add(new DriverDTO(
                    rs.getInt("driver_id"),
                    rs.getString("driver_name"),
                    rs.getString("nic_number"),
                    rs.getString("contact_number"),
                    DriverStatus.valueOf(rs.getString("driver_status"))
                ));
            }
        }
        return driverList;
    }

    @Override
    public int getDriversCount(String search, int limit, int offset) throws SQLException {
        String sql = "SELECT COUNT(*) FROM driver WHERE (LOWER(driver_name) LIKE LOWER(?) OR LOWER(nic_number) LIKE LOWER(?)) AND is_delete != 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setString(2, searchParam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public boolean deleteDriver(int driverId) throws SQLException {
        String sql = "UPDATE driver SET is_delete = 1 WHERE driver_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public DriverDTO getDriverById(int driverId) throws SQLException {
        String sql = "SELECT driver_id, driver_name, nic_number, contact_number, driver_status FROM driver WHERE driver_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new DriverDTO(
                    rs.getInt("driver_id"),
                    rs.getString("driver_name"),
                    rs.getString("nic_number"),
                    rs.getString("contact_number"),
                    DriverStatus.valueOf(rs.getString("driver_status"))
                );
            }
        }
        return null;
    }

    @Override
    public boolean updateDriver(Driver driver) throws SQLException {
        String query = "UPDATE driver SET driver_name = ?, nic_number = ?, contact_number = ?, driver_status = ? WHERE driver_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, driver.getDriverName());
            stmt.setString(2, driver.getNicNumber());
            stmt.setString(3, driver.getContactNumber());
            stmt.setString(4, driver.getDriverStatus().name());
            stmt.setInt(5, driver.getDriverId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

	@Override
	public List<DriverDTO> getDriversId() throws SQLException {
        List<DriverDTO> driverList = new ArrayList<>();
        String sql = "SELECT driver_id, driver_name, nic_number FROM driver WHERE driver_status = ? AND is_delete != 1 ORDER BY driver_id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, DriverStatus.AVAILABLE.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                driverList.add(new DriverDTO(
                    rs.getInt("driver_id"),
                    rs.getString("driver_name"),
                    rs.getString("nic_number")
                ));
            }
        }
        return driverList;
	}
}
