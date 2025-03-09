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

import com.bms.controller.CustomerController;
import com.bms.controller.StaffController;
import com.bms.dao.CustomerDAO;
import com.bms.dao.CustomerDAOImpl;
import com.bms.dao.StaffDAO;
import com.bms.dao.StaffDAOImpl;
import com.bms.dto.CustomerDTO;
import com.bms.dto.StaffDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/profile")
public class CustomerProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerController customerController;
	
	@Override
    public void init() {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        this.customerController = new CustomerController(customerDAO);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		if (!AuthUtils.isAuthenticated(request, response)) {
	        return;
	    }

	    Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER,AccountType.STAFF,AccountType.CUSTOMER);
	    boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
	    if (!authorized) {
	        return;
	    }
	    
	    HttpSession session = request.getSession(false);
        Integer customerId = InputValidator.parseInteger(session.getAttribute("customerId").toString());

	    if (customerId == null || customerId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Customer ID");
	        return;
	    }

	    try {
	        CustomerDTO customerDTO = customerController.getCustomerById(customerId);
	        
	        if (Objects.isNull(customerDTO)) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
	            return;
	        }

	        request.setAttribute("customer", customerDTO);
	        request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
	        
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

	    Integer customerId = InputValidator.parseInteger(request.getParameter("customerId"));
	    if (customerId == null || customerId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Customer ID");
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
        	
	        CustomerDTO exsistingCustomer = customerController.getCustomerById(customerId);
    		request.setAttribute("customer", exsistingCustomer);

	        if (Objects.isNull(exsistingCustomer)) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer not found");
	            return;
	        }
	              
	        if(!email.equals(exsistingCustomer.getUserDTO().getUserEmail())) {
	        	if(customerController.isEmailDuplicate(email)) {
	                request.setAttribute("error", "Duplicate email please enter different email");
	                request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
	                return; 
	        	}	
	        }

	        if(!contactNumber.equals(exsistingCustomer.getContactNumber())) {
	         	if(customerController.isContactNumberDuplicate(contactNumber)) {
	                request.setAttribute("error", "Duplicate contact number please enter different contact");
	                request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
	                return;
	        	}
	        }

        	UserDTO userDTO = new UserDTO(exsistingCustomer.getUserDTO().getUserId(),email);
            CustomerDTO customerDto = new CustomerDTO(name, address, contactNumber,userDTO);
            
            boolean isUpdated = customerController.updateCustomer(customerDto);
            if (isUpdated) {
            	request.setAttribute("success","Profile updated successfully");
        		request.setAttribute("customer", customerDto);
    	        request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
            } 
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
	    
	}

}
