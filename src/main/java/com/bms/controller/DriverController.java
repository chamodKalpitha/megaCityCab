package com.bms.controller;

import java.sql.SQLException;
import java.util.List;

import com.bms.dao.DriverDAO;
import com.bms.dto.DriverDTO;
import com.bms.enums.DriverStatus;
import com.bms.model.Driver;
import com.bms.utils.InputValidator;

public class DriverController {

    private final DriverDAO driverDAO;

    public DriverController(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public boolean createDriver(DriverDTO driverDto) throws SQLException, IllegalArgumentException {
        InputValidator.isValidPhoneNumber(driverDto.getContactNumber());
        Driver driver = new Driver();
        driver.setDriverName(driverDto.getDriverName());
        driver.setNicNumber(driverDto.getNicNumber());
        driver.setContactNumber(driverDto.getContactNumber());
        driver.setDriverStatus(DriverStatus.AVAILABLE);
        return driverDAO.createDriver(driver);
    }

    public List<DriverDTO> getDrivers(String search, int limit, int offset) throws SQLException {
        return driverDAO.getDrivers(search, limit, offset);
    }

    public int getDriverCount(String search, int limit, int offset) throws SQLException {
        return driverDAO.getDriversCount(search, limit, offset);
    }

    public boolean deleteDriver(int driverId) throws SQLException {
        return driverDAO.deleteDriver(driverId);
    }

    public DriverDTO getDriverById(int driverId) throws SQLException {
        return driverDAO.getDriverById(driverId);
    }

    public boolean updateDriver(DriverDTO driverDto) throws SQLException, IllegalArgumentException {
        InputValidator.isValidPhoneNumber(driverDto.getContactNumber());
        Driver driver = new Driver();
        driver.setDriverId(driverDto.getDriverId());
        driver.setDriverName(driverDto.getDriverName());
        driver.setNicNumber(driverDto.getNicNumber());
        driver.setContactNumber(driverDto.getContactNumber());
        driver.setDriverStatus(driverDto.getDriverStatus());
        return driverDAO.updateDriver(driver);
    }
    
    public List<DriverDTO> getDriversID() throws SQLException {
        return driverDAO.getDriversId();
    }
}
