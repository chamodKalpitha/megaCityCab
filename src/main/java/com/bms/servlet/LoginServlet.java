package com.bms.servlet;
import java.io.IOException;
import java.sql.SQLException;

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
	                response.sendRedirect("dashboard/users");
	                return;
	            } 
	        }
	    }
	    request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        try {
            LoginDTO responseLoginDTO = loginController.login(loginDTO);
            
            if (responseLoginDTO==null) {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            } 
            
            if(responseLoginDTO.getAccountType() == AccountType.ADMIN) {
            	session.setAttribute("accountType", responseLoginDTO.getAccountType());
            	session.setAttribute("email", responseLoginDTO.getEmail());
                response.sendRedirect(request.getContextPath()+"/dashboard/users"); 
            }
            
        } catch (SQLException e) {
            System.out.println(e);
            request.setAttribute("error", "Internal Server Error");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            request.setAttribute("error", "Bad Request");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}
