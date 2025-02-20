package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;

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

/**
 * Servlet implementation class AddVehicleServlet
 */
@WebServlet("/admin/add-vehicle")
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
        if (!AuthUtils.isAuthorized(request, response, AccountType.ADMIN)) {
            return;
        }
        response.sendRedirect(request.getContextPath() + "/admin/new-vehicle.jsp");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleBrand = request.getParameter("vehicleBrand");
        String vehicleModel = request.getParameter("vehicleModel");
        String plateNumber = request.getParameter("plateNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        VehicleStatus vehicleStatus = VehicleStatus.valueOf(request.getParameter("vehicleStatus"));
        VehicleType vehicleType = VehicleType.valueOf(request.getParameter("vehicleType"));
        double ratePerKM = Double.parseDouble(request.getParameter("ratePerKM"));
        String vahicleImage = request.getParameter("vehicleImage");

        VehicleDTO vehicleDTO = new VehicleDTO(vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus, vehicleType, vahicleImage, ratePerKM);
        
        try {
            boolean isCreated = vehicleController.createVehicle(vehicleDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/admin/vehicles"); 
            } else {
                request.setAttribute("error", "Vehicle creation failed!");
                request.getRequestDispatcher("/admin/new-vehicle.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                request.setAttribute("error", "Duplicate plate number. Please enter a different plate number.");
                request.getRequestDispatcher("/admin/new-vehicle.jsp").forward(request, response);
                return;
            }
            request.setAttribute("error", "An error occurred while adding the vehicle.");
            request.getRequestDispatcher("/admin/new-vehicle.jsp").forward(request, response);
        }
    }

}
