package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
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
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class UpdateDriverServlet
 */
@WebServlet("/dashboard/update-driver")
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
        
        Integer driverId = InputValidator.parseInteger(request.getParameter("driverId"));
        if (driverId == null || driverId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
            return;
        }

        try {
        	
            DriverDTO driverDTO = driverController.getDriverById(driverId);
            if (Objects.isNull(driverDTO)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Driver not found");
                return;
            }
            
            request.setAttribute("driver", driverDTO);
            request.getRequestDispatcher("/dashboard/update-driver.jsp?driverId=" + driverId).forward(request, response);
            
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

	    Integer driverId = InputValidator.parseInteger(request.getParameter("driverId"));
	    if (driverId == null || driverId <= 0) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID");
	        return;
	    }

	    String driverName = request.getParameter("driverName");
	    String nicNumber = request.getParameter("nicNumber");
	    String contactNumber = InputValidator.isValidPhoneNumber(request.getParameter("contactNumber"));
	    DriverStatus driverStatus = InputValidator.parseDriverStatus(request.getParameter("driverStatus"));

	    if (driverName == null || nicNumber == null || contactNumber == null || driverStatus == null ||
	        driverName.isBlank() || nicNumber.isBlank() || contactNumber.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
	        return;
	    }


	    DriverDTO driverDTO = new DriverDTO(driverId, driverName.trim(), nicNumber.trim(), contactNumber.trim(), driverStatus);

	    try {
	        DriverDTO existingDriver = driverController.getDriverById(driverId);
	        
	        if (existingDriver == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Driver not found");
	            return;
	        }
	        
	        
	        if(!contactNumber.equals(existingDriver.getContactNumber()) || !nicNumber.equals(existingDriver.getNicNumber())) {
		        Boolean isDuplicateContactDetails = driverController.checkDuplicateDriver(nicNumber, contactNumber);
		        
		        if(isDuplicateContactDetails) {
		            request.setAttribute("driver", driverDTO);
	                request.setAttribute("error", "Duplicate NIC Number or contact Number");
	                request.getRequestDispatcher("/dashboard/update-driver.jsp?driverId=" + driverId).forward(request, response);
	                return;
		        }
	        }


	        boolean isUpdated = driverController.updateDriver(driverDTO);
	        if (isUpdated) {
	            response.sendRedirect(request.getContextPath() + "/dashboard/drivers");
	        } 

	    } catch (SQLException e) {
	    	
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            
	    }
	}

}
