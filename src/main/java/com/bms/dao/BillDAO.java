package com.bms.dao;

import java.sql.SQLException;

import com.bms.model.Bill;

public interface BillDAO {
	Bill create(Bill bill) throws SQLException;
}
