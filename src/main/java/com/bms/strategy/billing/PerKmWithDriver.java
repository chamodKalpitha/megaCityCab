package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public class PerKmWithDriver implements BillCalculator {

	@Override
	public double calculate(BillCalculateDateDTO data) {
		return (data.getKmCount() * data.getVehicleDTO().getRatePerKM())+ (data.getKmCount() * data.getDriverKmSalary());
	}

}
