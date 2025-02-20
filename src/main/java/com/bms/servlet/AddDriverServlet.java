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
import com.bms.utils.AuthUtils;

/**
 * Servlet implementation class AddDriverServlet
 */
@WebServlet("/admin/add-driver")
public class AddDriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DriverController driverController;

    public AddDriverServlet() {
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
        response.sendRedirect(request.getContextPath() + "/admin/new-driver.jsp");

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
		String driverName = request.getParameter("driverName");
        String nicNumber = request.getParameter("nicNumber");
        String contactNumber = request.getParameter("contactNumber");

        DriverDTO driverDTO = new DriverDTO(driverName, nicNumber, contactNumber);
        
        try {
            boolean isCreated = driverController.createDriver(driverDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/admin/drivers"); 
            } else {
                request.setAttribute("error", "Driver creation failed!");
                request.getRequestDispatcher("/admin/new-driver.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                request.setAttribute("error", "Duplicate NIC or Contact Number. Please enter different details.");
                request.getRequestDispatcher("/admin/new-driver.jsp").forward(request, response);
                return;
            }
            request.setAttribute("error", "An error occurred while adding the driver.");
            request.getRequestDispatcher("/admin/new-driver.jsp").forward(request, response);
        }
    }
	
}
