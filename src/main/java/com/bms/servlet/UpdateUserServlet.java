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

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/admin/update-user")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController userController;
	
	@Override
    public void init() {
        UserDAO userDAO = new UserDAOImpl();
        this.userController = new UserController(userDAO);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
		
        if (!authorized) {
            return;
        }
        
        int userId = request.getParameter("userId") != null ? Integer.parseInt(request.getParameter("userId")) : 0;
        if(userId==0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User");
	        return;
        }
        try {
			UserDTO userDTO = userController.getUserById(userId);
			request.setAttribute("user", userDTO);
	        request.getRequestDispatcher("/admin/update-user.jsp?userId="+userId).forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong try again!");

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
		
        int userId = request.getParameter("userId") != null ? Integer.parseInt(request.getParameter("userId")) : 0;
        if(userId==0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User");
	        return;
        }
        
	    String name = request.getParameter("name");
	    String email = request.getParameter("email");
	    AccountStatus accountStatus = AccountStatus.valueOf(request.getParameter("accountStatus"));
	    AccountType accountType = AccountType.valueOf(request.getParameter("accountType"));
	    
	    UserDTO userDTO = new UserDTO(userId, name, email,accountType,accountStatus);

		
	    
	    try {
            boolean isUpdated = userController.updateUser(userDTO);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/users"); 
            } else {
                request.setAttribute("error", "User creation failed!");
    	        request.getRequestDispatcher("/admin/update-user.jsp?userId="+userId).forward(request, response);
            }
        } catch (SQLException e) {
            if(e.getErrorCode()==1062) {
            	request.setAttribute("user", userDTO);
                request.setAttribute("error", "Duplicate email please enter different email");
    	        request.getRequestDispatcher("/admin/update-user.jsp?userId="+userId).forward(request, response);
                return;
            }
        	request.setAttribute("user", userDTO);

            request.setAttribute("error", "An error occurred while creating the user.");
	        request.getRequestDispatcher("/admin/update-user.jsp?userId="+userId).forward(request, response);
        } catch(IllegalArgumentException e) {
        	System.out.println(e);
        	request.setAttribute("user", userDTO);
            request.setAttribute("error", "An error occurred while creating the user.");
	        request.getRequestDispatcher("/admin/update-user.jsp?userId="+userId).forward(request, response);
        }
	}

}
