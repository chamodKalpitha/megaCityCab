package com.bms;
import java.sql.SQLException;
import java.util.Objects;
import com.bms.controller.LoginController;
import com.bms.dao.LoginDAO;
import com.bms.dao.LoginDAOImpl;
import com.bms.dto.LoginDTO;

public class App {
	public static void main(String[] args) {
		
		
		LoginDAO loginDAO = new LoginDAOImpl();
		LoginController loginController = new LoginController(loginDAO);
		
		
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("Shanthi@gmail.com");
        loginDTO.setPassword("123456789");
        
        try {
        	LoginDTO responseLoginDTO = loginController.login(loginDTO);
        	
        	if(Objects.isNull(responseLoginDTO)) {
            	System.out.println("Fail username password");
            }
        	
        	System.out.println(responseLoginDTO.getAccountType());

        } catch (SQLException  e) {
        	System.out.println(e);
        	System.out.println("Fail sql fail");
		} catch (IllegalArgumentException e) {
        	System.out.println("Fail");
        }
        
        
		
//        StaffDAOImpl staffDAO = new StaffDAOImpl();
//        StaffController staffController = new StaffController(staffDAO);
//
//        StaffDTO staffDTO = new StaffDTO();
//
//        staffDTO.setStaffName("Shanthi de Zoysaee");
//        staffDTO.setStaffEmail("Shani@gmail.com");
//        staffDTO.setAccountStatus(AccountStatus.ACTIVE);
//        staffDTO.setAccountType(AccountType.STAFF);
//        staffDTO.setPassword("123435435435435345343333");
//        try {
//			Staff createdStaff = staffController.createStaff(staffDTO);
//			System.out.print(createdStaff.getStaffId());
//		} catch (SQLException e) {
//			System.out.println(e.getErrorCode());
//
////			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
