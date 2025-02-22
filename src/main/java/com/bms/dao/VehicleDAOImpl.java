package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.dto.VehicleDTO;
import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;
import com.bms.model.Vehicle;

public class VehicleDAOImpl implements VehicleDAO {

    private final Connection connection;

    public VehicleDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }

    @Override
    public boolean createVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicle (vehicle_brand, vehicle_model, plate_number, capacity, vehicle_status, vehicle_type, rate_per_km, rate_per_day, image_url_string ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehicle.getVehicleBrand());
            stmt.setString(2, vehicle.getVehicleModel());
            stmt.setString(3, vehicle.getPlateNumber());
            stmt.setInt(4, vehicle.getCapacity());
            stmt.setString(5, vehicle.getVehicleStatus().name());
            stmt.setString(6, vehicle.getVehicleType().name());
            stmt.setDouble(7, vehicle.getRatePerKM());
            stmt.setDouble(8, vehicle.getRatePerDay());
            stmt.setString(9, vehicle.getImageURLString());
            stmt.executeUpdate();
            return true;
        }
    }

    @Override
    public List<VehicleDTO> getVehicles(String search, int limit, int offset) throws SQLException {
        List<VehicleDTO> vehicleList = new ArrayList<>();
        String sql = "SELECT vehicle_id, vehicle_brand, vehicle_model, plate_number, capacity, vehicle_status, vehicle_type, rate_per_km, rate_per_day, image_url_string FROM vehicle WHERE (LOWER(vehicle_brand) LIKE LOWER(?) OR LOWER(plate_number) LIKE LOWER(?)) AND is_delete != 1 ORDER BY vehicle_id ASC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setString(2, searchParam);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicleList.add(new VehicleDTO(
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
                ));
            }
        }
        return vehicleList;
    }
    
    @Override
    public int getVehiclesCount(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle WHERE (LOWER(vehicle_brand) LIKE LOWER(?) OR LOWER(plate_number) LIKE LOWER(?)) AND is_delete != 1";
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
    public boolean deleteVehicle(int vehicleId) throws SQLException {
        String sql = "UPDATE vehicle SET is_delete = 1 WHERE vehicle_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public VehicleDTO getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT vehicle_id, vehicle_brand, vehicle_model, plate_number, capacity, vehicle_status, vehicle_type,image_url_string, rate_per_km, rate_per_day FROM vehicle WHERE vehicle_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new VehicleDTO(
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
            }
        }
        return null;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicle SET vehicle_brand = ?, vehicle_model = ?, plate_number = ?, capacity = ?, vehicle_status = ?, vehicle_type = ?, image_url_string = ?, rate_per_km = ?,rate_per_day = ? WHERE vehicle_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getVehicleBrand());
            stmt.setString(2, vehicle.getVehicleModel());
            stmt.setString(3, vehicle.getPlateNumber());
            stmt.setInt(4, vehicle.getCapacity());
            stmt.setString(5, vehicle.getVehicleStatus().name());
            stmt.setString(6, vehicle.getVehicleType().name());
            stmt.setString(7, vehicle.getImageURLString());
            stmt.setDouble(8, vehicle.getRatePerKM());
            stmt.setDouble(9, vehicle.getRatePerDay());
            stmt.setInt(10, vehicle.getVehicleId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

	@Override
	public List<VehicleDTO> getVehiclesNumberPlate() throws SQLException {
		List<VehicleDTO> vehicleList = new ArrayList<>();
        String sql = "SELECT vehicle_id, plate_number,vehicle_brand,vehicle_model FROM vehicle WHERE is_delete != 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicleList.add(new VehicleDTO(
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_brand"),
                    rs.getString("vehicle_model"),
                    rs.getString("plate_number")
                ));
            }
        }
        return vehicleList;
	}

	@Override
	public List<VehicleDTO> getVehiclesForCustomer(String search, int limit, int offset) throws SQLException {
        List<VehicleDTO> vehicleList = new ArrayList<>();
        String sql = "SELECT vehicle_id, vehicle_brand, vehicle_model, capacity, vehicle_type, rate_per_km, rate_per_day, image_url_string " +
                "FROM vehicle " +
                "WHERE (LOWER(vehicle_brand) LIKE LOWER(?) OR LOWER(plate_number) LIKE LOWER(?)) " +
                "AND vehicle_status = 'AVAILABLE' " +
                "AND is_delete != 1 " +
                "ORDER BY vehicle_id ASC " +
                "LIMIT ? OFFSET ?";        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            ps.setString(2, searchParam);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicleList.add(new VehicleDTO(
                    rs.getInt("vehicle_id"),
                    rs.getString("vehicle_brand"),
                    rs.getString("vehicle_model"),
                    rs.getInt("capacity"),
                    VehicleType.valueOf(rs.getString("vehicle_type")),
                    rs.getString("image_url_string"),
                    rs.getDouble("rate_per_km"),
                    rs.getDouble("rate_per_day")
                ));
            }
        }
        return vehicleList;
	}
	
    @Override
    public int getVehiclesCountForCustomer(String search) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehicle WHERE (LOWER(vehicle_brand) LIKE LOWER(?) OR LOWER(plate_number) LIKE LOWER(?)) AND is_delete != 1 AND vehicle_status = 'AVAILABLE' ";
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
}
