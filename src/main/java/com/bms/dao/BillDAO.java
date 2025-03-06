package com.bms.dao;

import java.sql.SQLException;

import com.bms.dto.BillDTO;
import com.bms.model.Bill;

public interface BillDAO {
	public boolean createBill(Bill bill) throws SQLException;
	public BillDTO getBillById(int bookingId) throws SQLException;
}
