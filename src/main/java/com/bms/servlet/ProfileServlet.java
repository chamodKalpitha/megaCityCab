package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controller.StaffController;
import com.bms.dao.StaffDAO;
import com.bms.dao.StaffDAOImpl;
import com.bms.dto.StaffDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/dashboard/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StaffController staffController;
	
	@Override
    public void init() {
        StaffDAO staffDAO = new StaffDAOImpl();
        this.staffController = new StaffController(staffDAO);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		if (!AuthUtils.isAuthenticated(request, response)) {
	        return;
	    }

	    Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER,AccountType.STAFF);
	    boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
	    if (!authorized) {
	        return;
	    }
	    
	    HttpSession session = request.getSession(false);
        Integer staffId = InputValidator.parseInteger(session.getAttribute("staffId").toString());
        System.out.println(staffId);

	    if (staffId == null || staffId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
	        return;
	    }

	    try {
	    	
	        StaffDTO staffDTO = staffController.getStaffByUserId(staffId);
	        
	        if (Objects.isNull(staffDTO)) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found");
	            return;
	        }

	        request.setAttribute("staff", staffDTO);
	        request.getRequestDispatcher("/dashboard/profile.jsp").forward(request, response);
	        
	    } catch (SQLException e) {
	    	
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
	        
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    if (!AuthUtils.isAuthenticated(request, response)) {
	        return;
	    }

	    Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER,AccountType.CUSTOMER);
	    boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
	    if (!authorized) {
	        return;
	    }

	    Integer staffId = InputValidator.parseInteger(request.getParameter("staffId"));
	    if (staffId == null || staffId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
	        return;
	    }

	    String name = request.getParameter("name");
        String email = InputValidator.isValidEmail(request.getParameter("email"));
        String address = request.getParameter("address");
        String contactNumber = InputValidator.isValidPhoneNumber(request.getParameter("contactNumber"));

        if(Objects.isNull(name) || name.isBlank() || Objects.isNull(email) || Objects.isNull(address) || address.isBlank() || Objects.isNull(contactNumber)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
	        return;
        }
	    
        try {
        	
	        StaffDTO exsistingStaff = staffController.getStaffById(staffId);
    		request.setAttribute("staff", exsistingStaff);

	        if (Objects.isNull(exsistingStaff)) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found");
	            return;
	        }
	              
	        if(!email.equals(exsistingStaff.getUserDTO().getUserEmail())) {
	        	if(staffController.isEmailDuplicate(email)) {
	                request.setAttribute("error", "Duplicate email please enter different email");
	                request.getRequestDispatcher("/dashboard/profile.jsp").forward(request, response);
	                return; 
	        	}	
	        }

	        if(!contactNumber.equals(exsistingStaff.getContactNumber())) {
	         	if(staffController.isContactNumberDuplicate(contactNumber)) {
	                request.setAttribute("error", "Duplicate contact number please enter different contact");
	                request.getRequestDispatcher("/dashboard/profile.jsp").forward(request, response);
	                return;
	        	}
	        }

        	UserDTO userDTO = new UserDTO(exsistingStaff.getUserDTO().getUserId(),email);
            StaffDTO staffDTO = new StaffDTO(name, address, contactNumber,userDTO);
            
            boolean isUpdated = staffController.updateStaffProfile(staffDTO);
            if (isUpdated) {
            	request.setAttribute("success","Profile updated successfully");
    	        request.setAttribute("staff", staffDTO);
    	        request.getRequestDispatcher("/dashboard/profile.jsp").forward(request, response);
            } 
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
	    
	}

}
