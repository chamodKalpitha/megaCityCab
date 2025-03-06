package com.bms.controller;

import java.sql.SQLException;

import com.bms.dao.BillDAO;
import com.bms.dto.BillCalculateDateDTO;
import com.bms.dto.BillDTO;
import com.bms.model.Bill;
import com.bms.service.BillingService;


public class BillController {

    private final BillDAO billDAO;
    

    public BillController(BillDAO billDAO) {
        this.billDAO = billDAO;
    }
    
    public boolean createBill(BillCalculateDateDTO billCalculateDateDTO ) throws SQLException {
        BillingService billingService = new BillingService();
    	BillDTO billDTO = billingService.generateBill(billCalculateDateDTO);
    	Bill bill = new Bill(billCalculateDateDTO.getBookingDTO().getBookingId(), billCalculateDateDTO.getKmCount(), (int) billCalculateDateDTO.getDayCout(), billDTO.getTotalAmount(), billDTO.getTotalTax(), billCalculateDateDTO.getPaymentMethod());
        return billDAO.createBill(bill);
    }
    
    public BillDTO getBillById(int bookingId) throws SQLException {
        return billDAO.getBillById(bookingId);

    }
}
