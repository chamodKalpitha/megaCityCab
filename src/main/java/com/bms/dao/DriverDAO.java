package com.bms.dao;

import java.sql.SQLException;
import java.util.List;

import com.bms.dto.DriverDTO;
import com.bms.model.Driver;

public interface DriverDAO {
    boolean createDriver(Driver driver) throws SQLException;
    List<DriverDTO> getDrivers(String search, int limit, int offset) throws SQLException;
    int getDriversCount(String search) throws SQLException;
    boolean deleteDriver(int driverId) throws SQLException;
    DriverDTO getDriverById(int driverId) throws SQLException;
    boolean updateDriver(Driver driver) throws SQLException;
    List<DriverDTO> getDriversId() throws SQLException;
    boolean checkDuplicateDriver(String NICNumber, String contactNumber)throws SQLException;
}
