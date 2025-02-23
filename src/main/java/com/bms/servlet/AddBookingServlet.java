package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controller.BookingController;
import com.bms.controller.VehicleController;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.enums.PricingType;
import com.bms.utils.InputValidator;


@WebServlet("/customer/create-booking")
public class AddBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookingController bookingController;
	private VehicleController vehicleController;

    public AddBookingServlet() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.bookingController = new BookingController(bookingDAO);
        this.vehicleController = new VehicleController(vehicleDAO);
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); 

        Date bookingDate  = InputValidator.isValidDate(request.getParameter("bookingDate"));
        PricingType pricingType = InputValidator.parsePricingType(request.getParameter("pricingType"));
        Integer userId = InputValidator.parseInteger(session.getAttribute("customerId").toString());
        Integer vehicleId = InputValidator.parseInteger(request.getParameter("vehicleId"));
        
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int entries = request.getParameter("entries") != null ? InputValidator.parseInteger(request.getParameter("entries"))==null ? 10 : InputValidator.parseInteger(request.getParameter("entries")) : 10;
        int currentPage = request.getParameter("page") != null ? InputValidator.parseInteger(request.getParameter("page"))==null ? 10 : InputValidator.parseInteger(request.getParameter("page")) : 1;
       
        if(Objects.isNull(bookingDate)) {
            response.sendRedirect("/megaCityCab/vehicles?search="+ searchQuery + "&entries="+entries+"&page="+currentPage+"&error=Invalid Date");
            return;
        }
        
        if(Objects.isNull(pricingType)) {
            response.sendRedirect("/megaCityCab/vehicles?search="+ searchQuery + "&entries="+entries+"&page="+currentPage+"&error=Invalid Pricing type");
            return;
        }
        
        
        if(Objects.isNull(userId) || Objects.isNull(vehicleId)) {
            response.sendRedirect("/megaCityCab/vehicles?search="+ searchQuery + "&entries="+entries+"&page="+currentPage+"&error=Something went wrong try again");
        }
        
        BookingDTO bookingDTO = new BookingDTO(userId,vehicleId,bookingDate,pricingType);
        
        try {
        	
        	boolean isAvailable = vehicleController.checkVehicleAvailable(vehicleId);
        	if(!isAvailable) {
                response.sendRedirect("/megaCityCab/vehicles?search="+ searchQuery + "&entries="+entries+"&page="+currentPage+"&error=Sorry!. Someone booked vehicle minutes ago");
                return;
        	}
        	
			boolean isBooked = bookingController.createBooking(bookingDTO);
			if(isBooked) {
                response.sendRedirect("/megaCityCab/vehicles?search="+ searchQuery + "&entries="+entries+"&page="+currentPage+"&success=Vehicle booked successfully");
                return;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
		}
        
	}

}
