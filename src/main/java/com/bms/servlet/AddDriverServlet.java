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
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class AddDriverServlet
 */
@WebServlet("/dashboard/add-driver")
public class AddDriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DriverController driverController;

    public AddDriverServlet() {
        DriverDAO driverDAO = new DriverDAOImpl();
        this.driverController = new DriverController(driverDAO);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/dashboard/new-driver.jsp");

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
		
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        
		String driverName = request.getParameter("driverName");
        String nicNumber = request.getParameter("nicNumber");
        String contactNumber = request.getParameter("contactNumber");
        
        if(Objects.isNull(driverName) || driverName.isBlank() || Objects.isNull(nicNumber) || nicNumber.isBlank() || Objects.isNull(contactNumber) || contactNumber.isBlank()) {
	        request.setAttribute("error", "All fields are required.");
	        request.getRequestDispatcher("/dashboard/new-driver.jsp").forward(request, response);
	        return;
        }

        
        try {
        	
            DriverDTO driverDTO = new DriverDTO(driverName, nicNumber, contactNumber);
            boolean isCreated = driverController.createDriver(driverDTO);
            
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/dashboard/drivers"); 
            }
            
        } catch (SQLException e) {
        	
            if (e.getErrorCode() == 1062) {
                request.setAttribute("error", "Duplicate NIC or Contact Number. Please enter different details.");
                request.getRequestDispatcher("/dashboard/new-driver.jsp").forward(request, response);
                return;
            }
            
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
	
}
