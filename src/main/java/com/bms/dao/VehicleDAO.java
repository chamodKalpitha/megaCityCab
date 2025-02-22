package com.bms.dao;

import java.sql.SQLException;
import java.util.List;

import com.bms.dto.VehicleDTO;
import com.bms.model.Vehicle;

public interface VehicleDAO {
    boolean createVehicle(Vehicle vehicle) throws SQLException;
    List<VehicleDTO> getVehicles(String search, int limit, int offset) throws SQLException;
    List<VehicleDTO> getVehiclesForCustomer(String search, int limit, int offset) throws SQLException;
    int getVehiclesCount(String search) throws SQLException;
    int getVehiclesCountForCustomer(String search) throws SQLException;
    boolean deleteVehicle(int vehicleId) throws SQLException;
    VehicleDTO getVehicleById(int vehicleId) throws SQLException;
    boolean updateVehicle(Vehicle vehicle) throws SQLException;
    List<VehicleDTO> getVehiclesNumberPlate() throws SQLException;

}
