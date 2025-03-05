package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public interface BillCalculator {
	double calculate(BillCalculateDateDTO data);
}
