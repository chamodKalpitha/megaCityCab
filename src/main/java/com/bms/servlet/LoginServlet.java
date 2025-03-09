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

import com.bms.controller.LoginController;
import com.bms.dao.LoginDAO;
import com.bms.dao.LoginDAOImpl;
import com.bms.dto.LoginDTO;
import com.bms.enums.AccountType;
import com.bms.utils.InputValidator;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginController loginController;
	
	@Override
    public void init() {
        LoginDAO loginDAO = new LoginDAOImpl();
        this.loginController = new LoginController(loginDAO);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession(false); 
	    if (session != null) {
	        Object accountTypeObject = session.getAttribute("accountType");
	        if (accountTypeObject != null) {
	 	            AccountType accountType = AccountType.valueOf(accountTypeObject.toString());
	            if (accountType.equals(AccountType.ADMIN)) {
	                response.sendRedirect("dashboard/staffs");
	                return;
	            } 
	            
	            if (accountType.equals(AccountType.CUSTOMER)) {
	                response.sendRedirect(request.getContextPath()+"/vehicles");
	                return;
	            } 
	        }
	    }
	    request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String userEmail = request.getParameter("email");
        String password = request.getParameter("password");
        
        if(Objects.isNull(userEmail) || userEmail.isBlank() || Objects.isNull(password) || password.isBlank()) {
        	request.setAttribute("error", "All fields are required.");
        	request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        
        if(Objects.isNull(InputValidator.isValidEmail(userEmail))) {
            request.setAttribute("error", "Invalid Email");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        if(Objects.isNull(InputValidator.isValidPassword(password))) {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        
        HttpSession session = request.getSession();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(userEmail);
        loginDTO.setPassword(password);

        try {
            LoginDTO responseLoginDTO = loginController.login(loginDTO);
            
            if (responseLoginDTO==null) {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            } 
            
            if(responseLoginDTO.getAccountType() == AccountType.ADMIN) {
            	session.setAttribute("staffId", responseLoginDTO.getUserId());
            	session.setAttribute("accountType", responseLoginDTO.getAccountType());
                response.sendRedirect(request.getContextPath()+"/dashboard/staffs"); 
            }
            
            if(responseLoginDTO.getAccountType() == AccountType.MANAGER || responseLoginDTO.getAccountType() == AccountType.STAFF) {
            	session.setAttribute("staffId", responseLoginDTO.getUserId());
            	session.setAttribute("accountType", responseLoginDTO.getAccountType());
                response.sendRedirect(request.getContextPath()+"/dashboard/bookings"); 
            }
            
            if(responseLoginDTO.getAccountType() == AccountType.CUSTOMER) {
            	session.setAttribute("customerId", responseLoginDTO.getUserId());
            	session.setAttribute("accountType", responseLoginDTO.getAccountType());
                response.sendRedirect(request.getContextPath()+"/vehicles"); 
            } 
            
        } catch (SQLException e) {
        	
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
        }
    }

}
