package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.controller.BookingController;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class DeleteBooking
 */
@WebServlet("/cancel-booking")
public class CustomerCancelBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookingController bookingController;

    public CustomerCancelBooking() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        this.bookingController = new BookingController(bookingDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("hit");
		HttpSession session = request.getSession(false); 

    	if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
    	
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.CUSTOMER,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        Integer userId = InputValidator.parseInteger(session.getAttribute("customerId").toString());

        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? InputValidator.parseInteger(request.getParameter("entries")) : 10;
            int bookingId = request.getParameter("bookingId") != null ? InputValidator.parseInteger(request.getParameter("bookingId")) : 0;
            
            BookingDTO bookingDTO = bookingController.getBookingById(bookingId);
            
            if(bookingDTO.getUserId()!=userId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden cancel booking request");
                return;
            }
            
            boolean isDeleted = bookingController.calcelBooking(bookingId);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/bookings?search=" + searchQuery + "&entries=" + entries);
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
        
    }
}
