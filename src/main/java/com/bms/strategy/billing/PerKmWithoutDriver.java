package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public class PerKmWithoutDriver implements BillCalculator {

	@Override
	public double calculate(BillCalculateDateDTO data) {
		System.out.println(data.getKmCount());
		return data.getKmCount() * data.getVehicleDTO().getRatePerKM();
	}

}
