package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.DriverController;
import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DeleteDriverServlet
 */
@WebServlet("/admin/delete-driver")
public class DeleteDriverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DriverController driverController;

    public DeleteDriverServlet() {
        DriverDAO driverDAO = new DriverDAOImpl();
        this.driverController = new DriverController(driverDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("hit");
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
        if (!AuthUtils.isAuthorized(request, response, AccountType.ADMIN)) {
            return;
        }

        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
        int driverId = request.getParameter("driverId") != null ? Integer.parseInt(request.getParameter("driverId")) : 0;

        try {
            boolean isDeleted = driverController.deleteDriver(driverId);
            
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/admin/drivers?search=" + searchQuery + "&entries=" + entries);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to delete driver");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            e.printStackTrace();
        }
    }
}
