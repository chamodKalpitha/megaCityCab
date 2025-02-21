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
@WebServlet("/admin/delete-booking")
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
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN,AccountType.STAFF,AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
		
        if (!authorized) {
            return;
        }

        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
        int bookingId = request.getParameter("bookingId") != null ? Integer.parseInt(request.getParameter("bookingId")) : 0;

        try {
            boolean isDeleted = bookingController.deleteBooking(bookingId);
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/admin/bookings?search=" + searchQuery + "&entries=" + entries);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to delete booking");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
