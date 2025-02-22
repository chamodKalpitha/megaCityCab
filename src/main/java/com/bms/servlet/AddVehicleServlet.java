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

@WebServlet("/dashboard/add-vehicle")
public class AddVehicleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private VehicleController vehicleController;

    public AddVehicleServlet() {
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);
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
		
        response.sendRedirect(request.getContextPath() + "/dashboard/new-vehicle.jsp");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
    	
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
		if (!authorized) {
            return;
        }
		
        String vehicleBrand = request.getParameter("vehicleBrand");
        String vehicleModel = request.getParameter("vehicleModel");
        String plateNumber = request.getParameter("plateNumber");
        int capacity = InputValidator.parseInteger(request.getParameter("capacity"));
        VehicleStatus vehicleStatus = InputValidator.parseVehicleStatus(request.getParameter("vehicleStatus"));
        VehicleType vehicleType = InputValidator.parseVehicleType(request.getParameter("vehicleType"));
        double ratePerKM = InputValidator.parseDouble(request.getParameter("ratePerKM"));
        double ratePerDay = InputValidator.parseDouble(request.getParameter("ratePerDay"));
        String vahicleImage = request.getParameter("vehicleImage");
        
        if(Objects.isNull(vehicleBrand) || Objects.isNull(vehicleModel) || Objects.isNull(plateNumber) || Objects.isNull(capacity) || Objects.isNull(vehicleStatus) ||
        		Objects.isNull(vehicleType) || Objects.isNull(ratePerKM) || Objects.isNull(ratePerDay) || Objects.isNull(vahicleImage)
        		|| vehicleBrand.isBlank() || vehicleModel.isBlank() || plateNumber.isBlank() || vahicleImage.isBlank()) {
        	request.setAttribute("error", "All fields are required.");
        	request.getRequestDispatcher("/dashboard/new-vehicle.jsp").forward(request, response);
        }
             
        try {
        	
            VehicleDTO vehicleDTO = new VehicleDTO(vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus, vehicleType, vahicleImage, ratePerKM,ratePerDay);
            boolean isCreated = vehicleController.createVehicle(vehicleDTO);
            
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/vehicles"); 
            }
            
        } catch (SQLException e) {
        	
            if (e.getErrorCode() == 1062) {
                request.setAttribute("error", "Duplicate plate number. Please enter a different plate number.");
                request.getRequestDispatcher("/dashboard/new-vehicle.jsp").forward(request, response);
                return;
            }
            
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

}
