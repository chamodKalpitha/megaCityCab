package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.VehicleController;

import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.VehicleDTO;


/**
 * Servlet implementation class VehicleServlet
 */
@WebServlet("/dashboard/vehicles")
public class VehicleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VehicleController controller;
	
	@Override
    public void init() {
        VehicleDAO dao = new VehicleDAOImpl();
        this.controller = new VehicleController(dao);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int offset = (currentPage - 1) * entries;
            
			List<VehicleDTO> vehicles = controller.getVehicles(searchQuery, entries, offset);
			
			request.setAttribute("vehicles", vehicles);
	        request.setAttribute("page", currentPage);
	        request.setAttribute("count", controller.getVehicleCount(searchQuery));
	        request.setAttribute("entries", entries);
	        request.setAttribute("search", searchQuery);
	        
	        request.getRequestDispatcher("/dashboard/vehicles.jsp").forward(request, response);
	        
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
		} catch(NumberFormatException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
	        
		}
	}

}
