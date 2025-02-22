package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bms.controller.VehicleController;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DeleteVehicleServlet
 */
@WebServlet("/dashboard/delete-vehicle")
public class DeleteVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehicleController vehicleController;

    public DeleteVehicleServlet() {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
        
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }

        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int vehicleId = request.getParameter("vehicleId") != null ? Integer.parseInt(request.getParameter("vehicleId")) : 0;
            
            boolean isDeleted = vehicleController.deleteVehicle(vehicleId);
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/dashboard/vehicles?search=" + searchQuery + "&entries=" + entries);
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
