package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.StaffController;
import com.bms.dao.StaffDAO;
import com.bms.dao.StaffDAOImpl;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/dashboard/delete-staff")
public class DeleteStaffServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StaffController staffController;
	
    public DeleteStaffServlet() {
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
        
        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int userId = request.getParameter("userId") != null ? Integer.parseInt(request.getParameter("userId")) : 0;
            
			boolean isDeleted = staffController.deleteStaff(userId);
			if(isDeleted) {
				System.out.println("hit");

				response.sendRedirect(request.getContextPath()+"/dashboard/staffs?search="+searchQuery+"&entries="+entries); 
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
						
		} catch(NumberFormatException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
	        
		}
	}
}
