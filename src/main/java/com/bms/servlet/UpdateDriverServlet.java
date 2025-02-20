package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
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
import com.bms.enums.DriverStatus;
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class UpdateDriverServlet
 */
@WebServlet("/admin/update-driver")
public class UpdateDriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DriverController driverController;

    @Override
    public void init() {
        DriverDAO driverDAO = new DriverDAOImpl();
        this.driverController = new DriverController(driverDAO);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
		
        if (!authorized) {
            return;
        }
        int driverId = request.getParameter("driverId") != null ? Integer.parseInt(request.getParameter("driverId")) : 0;
        if (driverId == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
            return;
        }

        try {
            DriverDTO driverDTO = driverController.getDriverById(driverId);
            request.setAttribute("driver", driverDTO);
            request.getRequestDispatcher("/admin/update-driver.jsp?driverId=" + driverId).forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
		
        if (!authorized) {
            return;
        }
		
		 int driverId = request.getParameter("driverId") != null ? Integer.parseInt(request.getParameter("driverId")) : 0;
	        if (driverId == 0) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
	            return;
	        }

	        String driverName = request.getParameter("driverName");
	        String nicNumber = request.getParameter("nicNumber");
	        String contactNumber = request.getParameter("contactNumber");
	        DriverStatus driverStatus = DriverStatus.valueOf(request.getParameter("driverStatus"));

	        DriverDTO driverDTO = new DriverDTO(driverId, driverName, nicNumber, contactNumber, driverStatus);

	        try {
	            boolean isUpdated = driverController.updateDriver(driverDTO);
	            if (isUpdated) {
	                request.setAttribute("driver", driverDTO);
	                response.sendRedirect(request.getContextPath() + "/admin/drivers");
	            } else {
	                request.setAttribute("driver", driverDTO);
	                request.setAttribute("error", "Driver update failed!");
	                request.getRequestDispatcher("/admin/update-driver.jsp?driverId=" + driverId).forward(request, response);
	            }
	        } catch (SQLException e) {
	            request.setAttribute("driver", driverDTO);
	            request.setAttribute("error", "An error occurred while updating the driver.");
	            request.getRequestDispatcher("/admin/update-driver.jsp?driverId=" + driverId).forward(request, response);
	        } catch (IllegalArgumentException e) {
	            System.out.println(e);
	            request.setAttribute("driver", driverDTO);
	            request.setAttribute("error", "Invalid input provided.");
	            request.getRequestDispatcher("/admin/update-driver.jsp?driverId=" + driverId).forward(request, response);
	        }
	}

}
