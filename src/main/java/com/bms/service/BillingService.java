package com.bms.service;

import com.bms.dto.BillCalculateDateDTO;
import com.bms.dto.BillDTO;
import com.bms.enums.PricingType;
import com.bms.strategy.billing.BillCalculator;
import com.bms.strategy.billing.PerKmWithDriver;
import com.bms.strategy.billing.PerKmWithoutDriver;

public class BillingService {
	
	private BillCalculator getCalculator(PricingType pricingType) {
		switch (pricingType) {
		case PER_KM_WITH_DRIVER: {
			return new PerKmWithDriver();
		}
		case PER_KM_WITHOUT_DRIVER: {
			return new PerKmWithoutDriver();
		}
		default:
			throw new IllegalArgumentException("Invalid pricing type");
		}
	}
	
    public BillDTO generateBill(BillCalculateDateDTO billCalculateDateDTO) {
    	BillCalculator calculator = getCalculator(billCalculateDateDTO.getBookingDTO().getPricingType());
        double subtotal = calculator.calculate(billCalculateDateDTO);
        double tax = subtotal * 0.18;
        BillDTO billDTO = new BillDTO(subtotal, tax);
        return billDTO; 
    }

}
