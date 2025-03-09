package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.BookingController;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DeleteBooking
 */
@WebServlet("/dashboard/delete-booking")
public class DeleteBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookingController bookingController;

    public DeleteBooking() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        this.bookingController = new BookingController(bookingDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
    	
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.CUSTOMER,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }

        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int bookingId = request.getParameter("bookingId") != null ? Integer.parseInt(request.getParameter("bookingId")) : 0;
            
            boolean isDeleted = bookingController.deleteBooking(bookingId);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/dashboard/bookings?search=" + searchQuery + "&entries=" + entries);
            }
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        } catch(NumberFormatException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
	        
		}
        
    }
}
