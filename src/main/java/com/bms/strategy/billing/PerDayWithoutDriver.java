package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public class PerDayWithoutDriver implements BillCalculator {

	@Override
	public double calculate(BillCalculateDateDTO data) {
		return data.getDayCout() * data.getVehicleDTO().getRatePerDay();

	}

}
