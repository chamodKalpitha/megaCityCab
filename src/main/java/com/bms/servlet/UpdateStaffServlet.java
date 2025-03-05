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

import com.bms.controller.StaffController;
import com.bms.dao.StaffDAO;
import com.bms.dao.StaffDAOImpl;
import com.bms.dto.StaffDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/dashboard/update-staff")
public class UpdateStaffServlet extends HttpServlet {
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

	    Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN);
	    boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
	    if (!authorized) {
	        return;
	    }

	    Integer staffId = InputValidator.parseInteger(request.getParameter("staffId"));
	    if (staffId == null || staffId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
	        return;
	    }

	    try {
	    	
	        StaffDTO staffDTO = staffController.getStaffById(staffId);
	        
	        if (Objects.isNull(staffDTO)) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found");
	            return;
	        }

	        request.setAttribute("staff", staffDTO);
	        request.getRequestDispatcher("/dashboard/update-staff.jsp").forward(request, response);
	        
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

	    Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN);
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
        AccountStatus accountStatus = InputValidator.parseAccountStatus(request.getParameter("accountStatus"));
        AccountType accountType = InputValidator.parseAccountType(request.getParameter("accountType"));

        if(Objects.isNull(name) || name.isBlank() || Objects.isNull(email) || Objects.isNull(address) || address.isBlank() || Objects.isNull(contactNumber) || Objects.isNull(accountStatus) || Objects.isNull(accountType)) {
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
        	
        	if(staffController.isEmailDuplicate(email)) {
                request.setAttribute("error", "Duplicate email please enter different email");
                request.getRequestDispatcher("/dashboard/update-staff.jsp?staffId=" + staffId).forward(request, response);
                return; 
        	}

        	
         	if(staffController.isContactNumberDuplicate(contactNumber)) {
                request.setAttribute("error", "Duplicate contact number please enter different contact");
                request.getRequestDispatcher("/dashboard/update-staff.jsp?staffId=" + staffId).forward(request, response);
                return;
        	}
         	
        	UserDTO userDTO = new UserDTO(exsistingStaff.getUserDTO().getUserId(),email,accountType,accountStatus);
            StaffDTO staffDTO = new StaffDTO(name, address, contactNumber,userDTO);
            
            boolean isCreated = staffController.updateStaff(staffDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/staffs"); 
            } 
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
	    
	}

}
