package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controller.BookingController;
import com.bms.controller.DriverController;
import com.bms.controller.VehicleController;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.dto.DriverDTO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PricingType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

@WebServlet("/update-booking")
public class CustomerUpdateBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingController bookingController;
    private VehicleController vehicleController;
    private DriverController driverController;

    @Override
    public void init() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        this.bookingController = new BookingController(bookingDAO);

        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);

        DriverDAO driverDAO = new DriverDAOImpl();
        this.driverController = new DriverController(driverDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }

        Integer bookingId = InputValidator.parseInteger(request.getParameter("bookingId"));
        if (bookingId == null || bookingId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }

        try {
        	
            BookingDTO bookingDTO = bookingController.getBookingById(bookingId);
            if (Objects.isNull(bookingDTO)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
                return;
            }

            List<VehicleDTO> vehicles = vehicleController.getVehiclesNumberPlate();
            List<DriverDTO> drivers = driverController.getDriversID();
            
            request.setAttribute("booking", bookingDTO);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("drivers", drivers);
            
            request.getRequestDispatcher("/customer/update-booking.jsp?bookingId=" + bookingId).forward(request, response);
            
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
        HttpSession session = request.getSession(false); 
        Integer bookingId = InputValidator.parseInteger(request.getParameter("bookingId"));
        if (bookingId == null || bookingId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }
          
        Integer userId = InputValidator.parseInteger(session.getAttribute("customerId").toString());
        Integer bookedVehicleId = InputValidator.parseInteger(request.getParameter("bookedVehicleId"));
        Date bookingDate  = InputValidator.isValidDate(request.getParameter("bookingDate"));
        PricingType pricingType = InputValidator.parsePricingType(request.getParameter("pricingType"));
        
	    if (bookedVehicleId == null || bookingDate == null || pricingType == null) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
		        return;
		    }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingId);
        bookingDTO.setBookedVehicleId(bookedVehicleId);
        bookingDTO.setBookingDate(bookingDate);
        bookingDTO.setPricingType(pricingType);

        
        try {
        	
            BookingDTO existingBooking = bookingController.getBookingById(bookingId);
            if (Objects.isNull(existingBooking)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
                return;
            }
            
            if(existingBooking.getUserId()!=userId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden update booking request");
                return;
            }
            
            boolean isUpdated = bookingController.updateBookingByCustomer(bookingDTO);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/bookings");
                return;
            } 

        } catch (SQLException e) {
        	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            
        }
    }
}
