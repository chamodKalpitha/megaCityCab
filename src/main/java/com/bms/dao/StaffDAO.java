package com.bms.dao;

import java.sql.SQLException;
import java.util.List;

import com.bms.dto.StaffDTO;
import com.bms.model.Staff;
import com.bms.model.User;

public interface StaffDAO {
	public boolean createStaff(User user, Staff staff ) throws SQLException;
	public List<StaffDTO> getStaffs(String search, int limit, int offset) throws SQLException;
	public int getStaffsCount(String search) throws SQLException;
	public boolean deleteStaff(int staffId) throws SQLException;
	public StaffDTO getStaffById(int staffId) throws SQLException;
	public StaffDTO getStaffByUserId(int userId) throws SQLException;
	public boolean updateStaff(User user, Staff staff ) throws SQLException;
	public boolean isEmailDuplicate(String email) throws SQLException;
    public boolean isContactNumberDuplicate(String contactNumber) throws SQLException;
	public boolean updateStaffProfile(User user, Staff staff ) throws SQLException;
}
