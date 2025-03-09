package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controller.CustomerController;
import com.bms.dao.CustomerDAO;
import com.bms.dao.CustomerDAOImpl;
import com.bms.dto.CustomerDTO;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountType;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class CustomerRegisterServlet
 */
@WebServlet("/register")
public class CustomerRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CustomerController customerController;
    
    public CustomerRegisterServlet() {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        this.customerController = new CustomerController(customerDAO);
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false); 
	    if (session != null) {
	        Object accountTypeObject = session.getAttribute("accountType");
	        if (accountTypeObject != null) {
	 	            AccountType accountType = AccountType.valueOf(accountTypeObject.toString());
	            if (accountType.equals(AccountType.ADMIN)) {
	                response.sendRedirect("dashboard/users");
	                return;
	            }
	            if(accountType.equals(AccountType.MANAGER) || accountType.equals(AccountType.STAFF)){
	                response.sendRedirect("dashboard/bookings");
	                return;
	            }
	            if(accountType.equals(AccountType.CUSTOMER)){
	                response.sendRedirect("/vehicles");
	                return;
	            }
	        }
	    }
	    request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String customerName = request.getParameter("customerName");
        String userEmail = request.getParameter("userEmail");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");
        String nicNumber = request.getParameter("nicNumber");
        String contactNumber = request.getParameter("contactNumber");
        
        if(Objects.isNull(customerName) || customerName.isBlank() || Objects.isNull(userEmail) || userEmail.isBlank() ||
        		Objects.isNull(password) || password.isBlank() || Objects.isNull(confirmPassword) || confirmPassword.isBlank() ||
        		Objects.isNull(address) || address.isBlank() || Objects.isNull(nicNumber) || nicNumber.isBlank() ||
        		Objects.isNull(contactNumber) || contactNumber.isBlank()) {
        	request.setAttribute("error", "All fields are required.");
        	request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }
        
        if(Objects.isNull(InputValidator.isValidEmail(userEmail))) {
            request.setAttribute("error", "Invalid Email");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }
        
        if(Objects.isNull(InputValidator.isValidPassword(password))) {
            request.setAttribute("error", "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }
        
        if(Objects.isNull(InputValidator.isValidPhoneNumber(contactNumber))) {
            request.setAttribute("error", "Invalid Mobile Number");
            request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
            return;
        }
        
        UserDTO userDTO = new UserDTO(userEmail, password);
        CustomerDTO customerDTO = new CustomerDTO(customerName, address, nicNumber, contactNumber);
        
        try {
        	
        	if (customerController.isEmailDuplicate(userEmail)) {
                request.setAttribute("error", "Email already exists. Please use a different email.");
                request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
                return;
            }

            if (customerController.isNicNumberDuplicate(nicNumber)) {
                request.setAttribute("error", "NIC number already exists. Please use a different NIC.");
                request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
                return;
            }

            if (customerController.isContactNumberDuplicate(contactNumber)) {
                request.setAttribute("error", "Contact number already exists. Please use a different number.");
                request.getRequestDispatcher("/customer/register.jsp").forward(request, response);
                return;
            }
        	
            boolean isRegistered = customerController.createCustomer(userDTO,customerDTO);
            
            if (isRegistered) {
                response.sendRedirect(request.getContextPath() + "/login.jsp?success=Registration successful. Please log in.");
                return;
            }
            
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
		} 

	}

}
