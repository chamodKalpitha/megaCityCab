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

import com.bms.controller.UserController;
import com.bms.dao.UserDAO;
import com.bms.dao.UserDAOImpl;
import com.bms.dto.UserDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/dashboard/add-user")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController userController;

    public AddUserServlet() {
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
        
        response.sendRedirect(request.getContextPath() + "/dashboard/new-users.jsp");
        
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
        String email = request.getParameter("email");
        AccountType accountType = InputValidator.parseAccountType(request.getParameter("accountType"));
        
        if(Objects.isNull(name) || name.isBlank() || Objects.isNull(email) || email.isBlank() || Objects.isNull(accountType)) {
	        request.setAttribute("error", "All fields are required.");
	        request.getRequestDispatcher("/dashboard/new-users.jsp").forward(request, response);
	        return;
        }
                
        try {
        	
            UserDTO userDTO = new UserDTO(name, email, accountType);
            boolean isCreated = userController.createUser(userDTO);
            
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/users"); 
            } 
            
        } catch (SQLException e) {
        	
            if(e.getErrorCode()==1062) {
                request.setAttribute("error", "Duplicate email please enter different email");
                request.getRequestDispatcher("/dashboard/new-users.jsp").forward(request, response);
                return;
            }
            
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        } catch(IllegalArgumentException e) {
        	
        	System.out.println(e);
            request.setAttribute("error", "An error occurred while creating the user.");
            request.getRequestDispatcher("/dashboard/new-users.jsp").forward(request, response);
            
        }
		
	}

}
