package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.BillController;
import com.bms.controller.BookingController;
import com.bms.controller.VehicleController;
import com.bms.dao.BillDAO;
import com.bms.dao.BillDAOImpl;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.BillCalculateDateDTO;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.AccountType;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentMethod;
import com.bms.enums.PricingType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class PrintBillServlet
 */
@WebServlet("/view-bill")
public class ViewBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookingController bookingController;
    private VehicleController vehicleController;
    private BillController billController;


    public ViewBillServlet() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        this.bookingController = new BookingController(bookingDAO);

        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);
        
        BillDAO billDAO = new BillDAOImpl();
        this.billController = new BillController(billDAO);

    }

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
            
            BillDTO billDTO = billController.getBillById(bookingId);
            
            if(Objects.nonNull(billDTO)) {
            	request.setAttribute("bill", billDTO);
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/receipt.jsp");
            	dispatcher.forward(request, response);
            	return;
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not ready yet!");
            return;
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
            
        }
	}

}
