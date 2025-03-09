package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public class PerKmWithoutDriver implements BillCalculator {

	@Override
	public double calculate(BillCalculateDateDTO data) {
		return data.getKmCount() * data.getVehicleDTO().getRatePerKM();
	}

}
