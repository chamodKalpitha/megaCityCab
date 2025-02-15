package com.bms.utils.validation;

import com.bms.dto.StaffDTO;

public class StaffValidator {
    public static void validate(StaffDTO staffDTO) {
        if (staffDTO.getEmpName() == null || staffDTO.getEmpName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name is required");
        }
        if (!isValidEmail(staffDTO.getEmpEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
