package com.bms.controller;

import java.sql.SQLException;
import java.util.List;

import com.bms.dao.VehicleDAO;
import com.bms.dto.DriverDTO;
import com.bms.dto.VehicleDTO;
import com.bms.model.Vehicle;

public class VehicleController {

    private final VehicleDAO vehicleDAO;

    public VehicleController(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean createVehicle(VehicleDTO vehicleDTO) throws SQLException {
    	Vehicle vehicle = new Vehicle(vehicleDTO.getVehicleBrand(),vehicleDTO.getVehicleModel(),vehicleDTO.getPlateNumber(),vehicleDTO.getCapacity(),vehicleDTO.getVehicleStatus(),vehicleDTO.getVehicleType(),vehicleDTO.getImageURLString(), vehicleDTO.getRatePerKM());
        return vehicleDAO.createVehicle(vehicle);
    }

    public List<VehicleDTO> getVehicles(String search, int limit, int offset) throws SQLException {
        return vehicleDAO.getVehicles(search, limit, offset);
    }

    public int getVehicleCount(String search, int limit, int offset) throws SQLException {
        return vehicleDAO.getVehiclesCount(search, limit, offset);
    }

    public VehicleDTO getVehicleById(int vehicleId) throws SQLException {
        return vehicleDAO.getVehicleById(vehicleId);
    }

    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
    	Vehicle vehicle = new Vehicle(vehicleDTO.getVehicleId(),vehicleDTO.getVehicleBrand(),vehicleDTO.getVehicleModel(),vehicleDTO.getPlateNumber(),vehicleDTO.getCapacity(),vehicleDTO.getVehicleStatus(),vehicleDTO.getVehicleType(),vehicleDTO.getImageURLString(),vehicleDTO.getRatePerKM());
        return vehicleDAO.updateVehicle(vehicle);
    }

    public boolean deleteVehicle(int vehicleId) throws SQLException {
        return vehicleDAO.deleteVehicle(vehicleId);
    }
}
