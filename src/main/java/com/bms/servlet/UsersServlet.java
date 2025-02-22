package com.bms.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

@WebServlet("/dashboard/users")
public class UsersServlet extends HttpServlet {
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
        
        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int offset = (currentPage - 1) * entries;
            
			List<UserDTO> users = userController.getUsers(searchQuery, entries, offset);
			
			request.setAttribute("users", users);
	        request.setAttribute("page", currentPage);
	        request.setAttribute("count", userController.getUserCount(searchQuery));
	        request.setAttribute("entries", entries);
	        request.setAttribute("search", searchQuery);
	        
	        request.getRequestDispatcher("/dashboard/users.jsp").forward(request, response);
	        
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
		} catch(NumberFormatException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
	        
		}

	}


}
