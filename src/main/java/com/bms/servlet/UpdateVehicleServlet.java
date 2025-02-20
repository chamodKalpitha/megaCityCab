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
 * Servlet implementation class UpdateVehicleServlet
 */
@WebServlet("/admin/update-vehicle")
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
        if (!AuthUtils.isAuthorized(request, response, AccountType.ADMIN)) {
            return;
        }
        int vehicleId = request.getParameter("vehicleId") != null ? Integer.parseInt(request.getParameter("vehicleId")) : 0;
        if (vehicleId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vehicle ID");
            return;
        }

        try {
            VehicleDTO vehicleDTO = vehicleController.getVehicleById(vehicleId);
            request.setAttribute("vehicle", vehicleDTO);
            request.getRequestDispatcher("/admin/update-vehicle.jsp?vehicleId=" + vehicleId).forward(request, response);
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
        if (!AuthUtils.isAuthorized(request, response, AccountType.ADMIN)) {
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

        VehicleDTO vehicleDTO = new VehicleDTO(vehicleId, vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus, vehicleType,vahicleImage, ratePerKM);

        try {
            boolean isUpdated = vehicleController.updateVehicle(vehicleDTO);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/vehicles");
            } else {
                request.setAttribute("error", "Vehicle update failed!");
                request.getRequestDispatcher("/admin/update-vehicle.jsp?vehicleId=" + vehicleId).forward(request, response);
            }
        } catch (SQLException e) {
        	System.out.println(e);
            request.setAttribute("error", "An error occurred while updating the vehicle.");
            request.getRequestDispatcher("/admin/update-vehicle.jsp?vehicleId=" + vehicleId).forward(request, response);
        } catch (IllegalArgumentException e) {
        	System.out.println(e);
            request.setAttribute("error", "Invalid input provided.");
            request.getRequestDispatcher("/admin/update-vehicle.jsp?vehicleId=" + vehicleId).forward(request, response);
        }
    }

}
