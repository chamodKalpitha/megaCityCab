package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.BookingController;
import com.bms.controller.DriverController;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.dto.DriverDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class Booking
 */
@WebServlet("/dashboard/bookings")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookingController controller;  
    
	@Override
    public void init() {
        BookingDAO dao = new BookingDAOImpl();
        this.controller = new BookingController(dao);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER, AccountType.STAFF);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
           
        try {
        	
            String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
            int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
            int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int offset = (currentPage - 1) * entries;
            
			List<BookingDTO> bookings = controller.getBookings(searchQuery, entries, offset);
			
			request.setAttribute("bookings", bookings);
	        request.setAttribute("page", currentPage);
	        request.setAttribute("count", controller.getBookingsCount(searchQuery));
	        request.setAttribute("entries", entries);
	        request.setAttribute("search", searchQuery);
	        
	        request.getRequestDispatcher("/dashboard/bookings.jsp").forward(request, response);
		} catch (SQLException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
	        
		} catch(NumberFormatException e) {
			
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
	        
		}
	}

}
