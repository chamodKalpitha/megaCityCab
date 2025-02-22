package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.UserController;
import com.bms.dao.UserDAO;
import com.bms.dao.UserDAOImpl;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/dashboard/update-user")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController userController;
	
	@Override
    public void init() {
        UserDAO userDAO = new UserDAOImpl();
        this.userController = new UserController(userDAO);
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

	    Integer userId = InputValidator.parseInteger(request.getParameter("userId"));
	    if (userId == null || userId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
	        return;
	    }

	    try {
	    	
	        UserDTO userDTO = userController.getUserById(userId);
	        if (userDTO == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
	            return;
	        }

	        request.setAttribute("user", userDTO);
	        request.getRequestDispatcher("/dashboard/update-user.jsp?userId=" + userId).forward(request, response);
	        
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

	    // Use InputValidator to safely parse userId
	    Integer userId = InputValidator.parseInteger(request.getParameter("userId"));
	    if (userId == null || userId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
	        return;
	    }

	    String name = request.getParameter("name");
	    String email = request.getParameter("email");

	    if (name == null || email == null ||
	    		name.isBlank() || email.isBlank()) {
	        request.setAttribute("error", "All fields are required.");
	        request.getRequestDispatcher("/dashboard/update-user.jsp?userId=" + userId).forward(request, response);
	        return;
	    }
	    
	    try {
	        // Validate enum values safely
	        AccountStatus accountStatus = AccountStatus.valueOf(request.getParameter("accountStatus"));
	        AccountType accountType = AccountType.valueOf(request.getParameter("accountType"));

	        UserDTO userDTO = new UserDTO(userId, name, email, accountType, accountStatus);

	        boolean isUpdated = userController.updateUser(userDTO);
	        if (isUpdated) {
	            response.sendRedirect(request.getContextPath() + "/dashboard/users");
	        } else {
	            request.setAttribute("error", "User update failed!");
	            request.setAttribute("user", userDTO);
	            request.getRequestDispatcher("/dashboard/update-user.jsp?userId=" + userId).forward(request, response);
	        }
	    } catch (SQLException e) {
	        request.setAttribute("user", new UserDTO(userId, name, email, null, null));
	        
	        if (e.getErrorCode() == 1062) {
	            request.setAttribute("error", "Duplicate email. Please enter a different email.");
	        } else {
	            request.setAttribute("error", "An error occurred while updating the user.");
	        }

	        request.getRequestDispatcher("/dashboard/update-user.jsp?userId=" + userId).forward(request, response);
	    } catch (IllegalArgumentException e) {
	        System.err.println("Invalid Enum Value: " + e.getMessage());
	        request.setAttribute("user", new UserDTO(userId, name, email, null, null));
	        request.setAttribute("error", "Invalid input provided.");
	        request.getRequestDispatcher("/dashboard/update-user.jsp?userId=" + userId).forward(request, response);
	    }
	}

}
