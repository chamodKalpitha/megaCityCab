package com.bms.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controller.BillController;
import com.bms.controller.BookingController;
import com.bms.controller.VehicleController;
import com.bms.dao.BillDAO;
import com.bms.dao.BillDAOImpl;
import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dao.VehicleDAO;
import com.bms.dao.VehicleDAOImpl;
import com.bms.dto.BillCalculateDateDTO;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.AccountType;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentMethod;
import com.bms.enums.PricingType;
import com.bms.utils.AuthUtils;
import com.bms.utils.InputValidator;

/**
 * Servlet implementation class PrintBillServlet
 */
@WebServlet("/dashboard/print-bill")
public class PrintBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookingController bookingController;
    private VehicleController vehicleController;
    private BillController billController;


    public PrintBillServlet() {
        BookingDAO bookingDAO = new BookingDAOImpl();
        this.bookingController = new BookingController(bookingDAO);

        VehicleDAO vehicleDAO = new VehicleDAOImpl();
        this.vehicleController = new VehicleController(vehicleDAO);
        
        BillDAO billDAO = new BillDAOImpl();
        this.billController = new BillController(billDAO);

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
        
        Integer bookingId = InputValidator.parseInteger(request.getParameter("bookingId"));
        if (bookingId == null || bookingId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }

        try {
        	
            BookingDTO bookingDTO = bookingController.getBookingById(bookingId); 

            if (Objects.isNull(bookingDTO)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
                return;
            }
            
            BillDTO billDTO = billController.getBillById(bookingId);
            
            if(Objects.nonNull(billDTO)) {
            	request.setAttribute("bill", billDTO);
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/receipt.jsp");
            	dispatcher.forward(request, response);
            	return;
            }

            List<VehicleDTO> vehicles = vehicleController.getVehiclesNumberPlate();
            
            request.setAttribute("booking", bookingDTO);
            request.setAttribute("vehicles", vehicles);
            
            request.getRequestDispatcher("/dashboard/print-bill.jsp?bookingId=" + bookingId).forward(request, response);
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong, try again!");
            
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtils.isAuthenticated(request, response)) {
            return;
        }
        
		Set<AccountType> allowedRoles = Set.of(AccountType.ADMIN, AccountType.MANAGER, AccountType.STAFF);
		boolean authorized = AuthUtils.isAuthorized(request, response, allowedRoles);
        if (!authorized) {
            return;
        }
        
        BillCalculateDateDTO billCalculateDateDTO = new BillCalculateDateDTO();
        
        Integer bookingId = InputValidator.parseInteger(request.getParameter("bookingId"));
        PaymentMethod paymentMethod = InputValidator.parsePaymentMethod(request.getParameter("paymentMethod"));
        
        if (bookingId == null || bookingId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Booking ID");
            return;
        }
        
        if(Objects.isNull(paymentMethod)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid payment method");
            return;
        }
        
        billCalculateDateDTO.setPaymentMethod(paymentMethod);
        
        try {
        	            
            BookingDTO bookingDTO = bookingController.getBookingById(bookingId);
            VehicleDTO vehicleDTO = vehicleController.getVehicleById(bookingDTO.getBookedVehicleId());
            billCalculateDateDTO.setVehicleDTO(vehicleDTO);
            
            if(Objects.isNull(bookingDTO) || Objects.isNull(vehicleDTO) || bookingDTO.getBookingStatus() != BookingStatus.COMPLETED) {
    	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
            }
            
            PricingType pricingType = bookingDTO.getPricingType();
            billCalculateDateDTO.setBookingDTO(bookingDTO);

            if(pricingType==PricingType.PER_KM_WITH_DRIVER) {
            	
                Double kmCount = InputValidator.parseDouble(request.getParameter("kmCount"));
                Double driverCharges = InputValidator.parseDouble(request.getParameter("driverCharges"));
                
                if(kmCount==null || driverCharges == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Km Count and Driver Charges required");
                    return;
                }
                billCalculateDateDTO.setKmCount(kmCount);
                billCalculateDateDTO.setDriverKmSalary(driverCharges);

            }
            
            if(pricingType==PricingType.PER_KM_WITHOUT_DRIVER) {
            	
                Double kmCount = InputValidator.parseDouble(request.getParameter("kmCount"));
                
                if(kmCount==null ) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Km Count required");
                    return;
                }
                billCalculateDateDTO.setKmCount(kmCount);
            }
            
            if(pricingType==PricingType.PER_DAY_WITH_DRIVER) {
            	
                Date completedDate = InputValidator.isValidDate(request.getParameter("completedDate"));
                Double driverCharges = InputValidator.parseDouble(request.getParameter("driverCharges"));

                if(completedDate==null || driverCharges==null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "completed Date count and Driver Charges required");
                    return;
                }
                
                // Convert Date to Calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(bookingDTO.getBookingDate());
                LocalDate startDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

                calendar.setTime(completedDate);
                LocalDate endDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

                // Calculate Day Count (Inclusive)
                long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;

                billCalculateDateDTO.setDayCout(dayCount);
                billCalculateDateDTO.setDriverDaySalary(driverCharges);

            }
            
            if(pricingType==PricingType.PER_DAY_WITHOUT_DRIVER) {
            	
                Date completedDate = InputValidator.isValidDate(request.getParameter("completedDate"));

                if(completedDate==null ) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "completed Date count required");
                    return;
                }
                
                // Convert Date to Calendar
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(bookingDTO.getBookingDate());
                LocalDate startDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

                calendar.setTime(completedDate);
                LocalDate endDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

                // Calculate Day Count (Inclusive)
                long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                
                billCalculateDateDTO.setDayCout(dayCount);

            }
              
            boolean isCreated = billController.createBill(billCalculateDateDTO);
            if(isCreated) {
                response.sendRedirect("/megaCityCab/dashboard/print-bill?bookingId=" + bookingId);
                return;
            }
            
            
        } catch (SQLException e) {
        	
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            
        }
	}

}
