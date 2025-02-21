package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@WebServlet("/admin/update-booking")
public class UpdateBookingServlet extends HttpServlet {
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

        int bookingId = request.getParameter("bookingId") != null ? Integer.parseInt(request.getParameter("bookingId")) : 0;
        if (bookingId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }

        try {
            BookingDTO bookingDTO = bookingController.getBookingById(bookingId);
            List<VehicleDTO> vehicles = vehicleController.getVehiclesNumberPlate();
            List<DriverDTO> drivers = driverController.getDriversID();
            request.setAttribute("booking", bookingDTO);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("drivers", drivers);
            request.getRequestDispatcher("/admin/update-booking.jsp?bookingId=" + bookingId).forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookingDTO bookingDTO;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
        
        int bookingId = request.getParameter("bookingId") != null ? Integer.parseInt(request.getParameter("bookingId")) : 0;
        if (bookingId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }

        int bookedVehicleId = Integer.parseInt(request.getParameter("bookedVehicleId"));
        Date bookingDate;
        
        try {
            bookingDate = formatter.parse(request.getParameter("bookingDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid booking date format");
            return;
        }

        BookingStatus bookingStatus = BookingStatus.valueOf(request.getParameter("bookingStatus"));
        PricingType pricingType = PricingType.valueOf(request.getParameter("pricingType"));
        
        if (pricingType == PricingType.PER_DAY_WITH_DRIVER || pricingType == PricingType.PER_KM_WITH_DRIVER) {
            Integer driverId = Integer.parseInt(request.getParameter("driverId"));
            bookingDTO = new BookingDTO(bookingId, bookedVehicleId, driverId, bookingDate, bookingStatus, pricingType);
        } else {
            bookingDTO = new BookingDTO(bookingId, bookedVehicleId, bookingDate, bookingStatus, pricingType);
        }

        try {
            boolean isUpdated = bookingController.updateBooking(bookingDTO);
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings");
            } else {
                request.setAttribute("error", "Booking update failed!");
                request.getRequestDispatcher("/admin/update-booking.jsp?bookingId=" + bookingId).forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "An error occurred while updating the booking.");
            request.getRequestDispatcher("/admin/update-booking.jsp?bookingId=" + bookingId).forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid input provided.");
            request.getRequestDispatcher("/admin/update-booking.jsp?bookingId=" + bookingId).forward(request, response);
        }
    }
}
