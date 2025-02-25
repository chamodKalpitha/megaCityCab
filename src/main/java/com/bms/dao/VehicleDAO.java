package com.bms.dao;

import java.sql.SQLException;
import java.util.List;

import com.bms.dto.VehicleDTO;
import com.bms.model.Vehicle;

public interface VehicleDAO {
    public boolean createVehicle(Vehicle vehicle) throws SQLException;
    public List<VehicleDTO> getVehicles(String search, int limit, int offset) throws SQLException;
    public List<VehicleDTO> getVehiclesForCustomer(String search, int limit, int offset) throws SQLException;
    public int getVehiclesCount(String search) throws SQLException;
    public int getVehiclesCountForCustomer(String search) throws SQLException;
    public boolean deleteVehicle(int vehicleId) throws SQLException;
    public VehicleDTO getVehicleById(int vehicleId) throws SQLException;
    public boolean updateVehicle(Vehicle vehicle) throws SQLException;
    public List<VehicleDTO> getVehiclesNumberPlate() throws SQLException;
    public boolean checkVehicleAvailable(int vehicleId) throws SQLException;

}
