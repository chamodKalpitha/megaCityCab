package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;

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

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/admin/add-user")
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
        if (!AuthUtils.isAuthorized(request, response, AccountType.ADMIN)) {
            return;
        }
        response.sendRedirect(request.getContextPath() + "/admin/new-users.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        AccountType accountType = AccountType.valueOf(request.getParameter("accountType"));
        UserDTO userDTO = new UserDTO(name, email, accountType);
        
        try {
            boolean isCreated = userController.createUser(userDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/admin/users"); 
            } else {
                request.setAttribute("error", "User creation failed!");
                request.getRequestDispatcher("/admin/new-users.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            if(e.getErrorCode()==1062) {
                request.setAttribute("error", "Duplicate email please enter different email");
                request.getRequestDispatcher("/admin/new-users.jsp").forward(request, response);
                return;
            }
            request.setAttribute("error", "An error occurred while creating the user.");
            request.getRequestDispatcher("/admin/new-users.jsp").forward(request, response);
        } catch(IllegalArgumentException e) {
        	System.out.println(e);
            request.setAttribute("error", "An error occurred while creating the user.");
            request.getRequestDispatcher("/admin/new-users.jsp").forward(request, response);
        }
		
	}

}
