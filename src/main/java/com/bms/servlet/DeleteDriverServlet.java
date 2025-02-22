package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.DriverController;
import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DeleteDriverServlet
 */
@WebServlet("/dashboard/delete-driver")
public class DeleteDriverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DriverController driverController;

    public DeleteDriverServlet() {
        DriverDAO driverDAO = new DriverDAOImpl();
        this.driverController = new DriverController(driverDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
        
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }

        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int driverId = request.getParameter("driverId") != null ? Integer.parseInt(request.getParameter("driverId")) : 0;
            
            boolean isDeleted = driverController.deleteDriver(driverId);
            
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/dashboard/drivers?search=" + searchQuery + "&entries=" + entries);
                return;
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
