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

import com.bms.controller.DriverController;
import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.dto.DriverDTO;
import com.bms.enums.AccountType;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class DriverServlet
 */
@WebServlet("/admin/drivers")
public class DriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DriverController driverController;
	
	@Override
    public void init() {
        DriverDAO dao = new DriverDAOImpl();
        this.driverController = new DriverController(dao);
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
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int entries = request.getParameter("entries") != null ? Integer.parseInt(request.getParameter("entries")) : 10;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int offset = (currentPage - 1) * entries;
        
        
        try {
			List<DriverDTO> drivers = driverController.getDrivers(searchQuery, entries, offset);
			request.setAttribute("drivers", drivers);
	        request.setAttribute("page", currentPage);
	        request.setAttribute("count", driverController.getDriverCount(searchQuery, entries, offset));
	        request.setAttribute("entries", entries);
	        request.setAttribute("search", searchQuery);
	        request.getRequestDispatcher("/admin/drivers.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		}
	}


}
