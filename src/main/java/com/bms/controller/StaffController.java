package com.bms.controller;

import java.sql.SQLException;
import java.util.List;

import com.bms.dao.StaffDAO;
import com.bms.dto.StaffDTO;
import com.bms.enums.AccountStatus;
import com.bms.model.Staff;
import com.bms.model.User;

public class StaffController {
	
    private StaffDAO staffDAO;
    
    public StaffController(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }
    
    public Boolean createStaff(StaffDTO staffDTO) throws SQLException, IllegalArgumentException {
    	String userPassword = "12345678";
    	User user = new User(staffDTO.getUserDTO().getUserEmail(),AccountStatus.ACTIVE,staffDTO.getUserDTO().getAccountType(),userPassword,false);
    	Staff staff = new Staff(staffDTO.getName(),staffDTO.getAddress(),staffDTO.getContactNumber());
    	return staffDAO.createStaff(user,staff);
    }
    
    public List<StaffDTO> getStaffs(String search, int limit, int offset) throws SQLException {
    	return staffDAO.getStaffs(search, limit, offset);
    }
    
    public int getStaffCount(String search) throws SQLException {
    	return staffDAO.getStaffsCount(search);
    }
    
    public boolean deleteStaff(int userId) throws SQLException {
    	return staffDAO.deleteStaff(userId);
    }
    
    public StaffDTO getStaffById(int userId) throws SQLException {
    	return staffDAO.getStaffById(userId);
    }
    
    public boolean updateStaff(StaffDTO staffDTO) throws SQLException, IllegalArgumentException{	
    	
    	User user = new User(staffDTO.getUserDTO().getUserId(),staffDTO.getUserDTO().getUserEmail(), staffDTO.getUserDTO().getAccountStatus(), staffDTO.getUserDTO().getAccountType());
    	Staff staff = new Staff(staffDTO.getName(),staffDTO.getAddress(),staffDTO.getContactNumber());
    	
    	return staffDAO.updateStaff(user,staff);
    	
    }
    
    public boolean isEmailDuplicate(String email) throws SQLException {
    	return staffDAO.isEmailDuplicate(email);
    }

    public boolean isContactNumberDuplicate(String contactNumber) throws SQLException {
    	return staffDAO.isContactNumberDuplicate(contactNumber);
    }
    
    public boolean updateStaffProfile(StaffDTO staffDTO) throws SQLException, IllegalArgumentException{	
    	
    	User user = new User(staffDTO.getUserDTO().getUserId(),staffDTO.getUserDTO().getUserEmail());
    	Staff staff = new Staff(staffDTO.getName(),staffDTO.getAddress(),staffDTO.getContactNumber());
    	
    	return staffDAO.updateStaffProfile(user,staff);
    	
    }
    
    public StaffDTO getStaffByUserId(int userId) throws SQLException {
    	return staffDAO.getStaffByUserId(userId);
    }
}
