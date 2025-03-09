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
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;


@WebServlet("/dashboard/add-staff")
public class AddStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StaffController staffController;

    public AddStaffServlet() {
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
        
        response.sendRedirect(request.getContextPath() + "/dashboard/new-staff.jsp");
        
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        
        String name = request.getParameter("name");
        String email = InputValidator.isValidEmail(request.getParameter("email"));
        String address = request.getParameter("address");
        String contactNumber = InputValidator.isValidPhoneNumber(request.getParameter("contactNumber"));
        AccountType accountType = InputValidator.parseAccountType(request.getParameter("accountType"));
        
        if(Objects.isNull(name) || name.isBlank() || Objects.isNull(email) || Objects.isNull(address) || address.isBlank() || Objects.isNull(contactNumber) || Objects.isNull(accountType)) {
	        request.setAttribute("error", "All fields are required.");
	        request.getRequestDispatcher("/dashboard/new-staff.jsp").forward(request, response);
	        return;
        }
                
        try {
        	
        	if(staffController.isEmailDuplicate(email)) {
                request.setAttribute("error", "Duplicate email please enter different email");
                request.getRequestDispatcher("/dashboard/new-staff.jsp").forward(request, response);
                return;
        	}
        	
        	if(staffController.isContactNumberDuplicate(contactNumber)) {
                request.setAttribute("error", "Duplicate contact number please enter different contact");
                request.getRequestDispatcher("/dashboard/new-staff.jsp").forward(request, response);
                return;
        	}
        	
        	UserDTO userDTO = new UserDTO(email, accountType);
            StaffDTO staffDTO = new StaffDTO(name, address, contactNumber,userDTO);
            
            boolean isCreated = staffController.createStaff(staffDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/staffs"); 
            } 
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
		
	}

}
