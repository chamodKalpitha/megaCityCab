package com.bms.strategy.billing;

import com.bms.dto.BillCalculateDateDTO;

public class PerDayWithDriver implements BillCalculator {

	@Override
	public double calculate(BillCalculateDateDTO data) {
		return (data.getDayCout() * data.getVehicleDTO().getRatePerDay())+ (data.getDayCout() * data.getDriverDaySalary());

	}

}
