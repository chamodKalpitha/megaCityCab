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

import com.bms.controller.VehicleController;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.VehicleDTO;
import com.bms.enums.AccountType;
import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateVehicleServlet
 */
@WebServlet("/dashboard/update-vehicle")
public class UpdateVehicleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private VehicleController vehicleController;

    @Override
    public void init() {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
    	
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
         
        Integer vehicleId = InputValidator.parseInteger(request.getParameter("vehicleId"));
        if (vehicleId == null || vehicleId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vehicle ID");
            return;
        }
        
        try {
        	
            VehicleDTO vehicleDTO = vehicleController.getVehicleById(vehicleId);
            if (Objects.isNull(vehicleDTO)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
                return;
            }
            
            request.setAttribute("vehicle", vehicleDTO);
            request.getRequestDispatcher("/dashboard/update-vehicle.jsp?vehicleId=" + vehicleId).forward(request, response);
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
            
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        int vehicleId = request.getParameter("vehicleId") != null ? Integer.parseInt(request.getParameter("vehicleId")) : 0;
        if (vehicleId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vehicle ID");
            return;
        }

        String vehicleBrand = request.getParameter("vehicleBrand");
        String vehicleModel = request.getParameter("vehicleModel");
        String plateNumber = request.getParameter("plateNumber");
        String vahicleImage = request.getParameter("vehicleImage");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        VehicleStatus vehicleStatus = VehicleStatus.valueOf(request.getParameter("vehicleStatus"));
        VehicleType vehicleType = VehicleType.valueOf(request.getParameter("vehicleType"));
        double ratePerKM = Double.parseDouble(request.getParameter("ratePerKM"));
        double ratePerDay = Double.parseDouble(request.getParameter("ratePerDay"));

        VehicleDTO vehicleDTO = new VehicleDTO(vehicleId, vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus, vehicleType,vahicleImage, ratePerKM,ratePerDay);

        try {

            VehicleDTO existingVehicleDTO = vehicleController.getVehicleById(vehicleId);
            if (Objects.isNull(existingVehicleDTO)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
                return;
            }
        	
        	Boolean isDuplicatePlateNumber = vehicleController.checkDuplicateVehicleNumberPlate(plateNumber);
        	        	
        	if(isDuplicatePlateNumber) {
                request.setAttribute("vehicle", existingVehicleDTO);
                request.setAttribute("error", "Duplicate vehicle number plate");
                request.getRequestDispatcher("/dashboard/update-vehicle.jsp?vehicleId="+vehicleId).forward(request, response);
                return;
        	}
        	
            boolean isUpdated = vehicleController.updateVehicle(vehicleDTO);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/vehicles");
            } 
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            
        } 
    }

}
