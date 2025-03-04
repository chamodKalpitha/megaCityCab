package com.bms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.bms.enums.AccountStatus;
import com.bms.enums.AccountType;
import com.bms.enums.BookingStatus;
import com.bms.enums.DriverStatus;
import com.bms.enums.PricingType;
import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;

public class InputValidator {


    public static String isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null; 
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()) {
            return null;
        }
        return email;
    }


    public static String isValidPassword(String password) {
        if (password == null || password.length() < 8) {
           return null;
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        if(!hasUpperCase && hasLowerCase && hasDigit) {
            return null;
        };
        return password;
    }
    
    public static Date isValidDate(String date) {
        if (date == null || date.isEmpty()) {
            return null; 
        }
        try {
            Date validDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return validDate;
        } catch (ParseException e) {
        	return null;
        }
    }
    
    public static String isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        if (!pattern.matcher(phoneNumber).matches()) {
            return null;
        }
        return phoneNumber;
    }
    
    public static Integer parseInteger(String value) {
        try {
            return (value != null) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static Double parseDouble(String value) {
        try {
            return (value != null) ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public static AccountType parseAccountType(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return AccountType.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static AccountStatus parseAccountStatus(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return AccountStatus.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static DriverStatus parseDriverStatus(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return DriverStatus.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static VehicleStatus parseVehicleStatus(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return VehicleStatus.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static VehicleType parseVehicleType(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return VehicleType.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static PricingType parsePricingType(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return PricingType.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public static BookingStatus parseBookingStatus(String value) {
        if (value == null || value.isEmpty()) {
            return null; 
        }
        try {
            return BookingStatus.valueOf(value.toUpperCase()); 
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
}
