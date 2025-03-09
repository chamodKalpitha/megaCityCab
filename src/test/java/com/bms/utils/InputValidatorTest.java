package com.bms.utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import org.junit.jupiter.api.Test;
import com.bms.enums.*;

class InputValidatorTest {

    @Test
    void testIsValidEmail() {
        assertEquals("test@example.com", InputValidator.isValidEmail("test@example.com"));
        assertNull(InputValidator.isValidEmail("invalid-email"));
        assertNull(InputValidator.isValidEmail(null));
    }

    @Test
    void testIsValidPassword() {
        assertEquals("StrongP@ss1", InputValidator.isValidPassword("StrongP@ss1"));
        assertNull(InputValidator.isValidPassword("weak"));
        assertNull(InputValidator.isValidPassword(null));
    }

    @Test
    void testIsValidDate() {
        assertNotNull(InputValidator.isValidDate("2023-10-01"));
        assertNull(InputValidator.isValidDate("invalid-date"));
        assertNull(InputValidator.isValidDate(null));
    }

    @Test
    void testIsValidPhoneNumber() {
        assertEquals("0712745590", InputValidator.isValidPhoneNumber("0712745590"));
        assertNull(InputValidator.isValidPhoneNumber("12345"));
    }

    @Test
    void testParseInteger() {
        assertEquals(123, InputValidator.parseInteger("123"));
        assertNull(InputValidator.parseInteger("invalid"));
        assertNull(InputValidator.parseInteger(null));
    }

    @Test
    void testParseDouble() {
        assertEquals(123.45, InputValidator.parseDouble("123.45"));
        assertNull(InputValidator.parseDouble("invalid"));
        assertNull(InputValidator.parseDouble(null));
    }

    @Test
    void testParseAccountType() {
        assertEquals(AccountType.ADMIN, InputValidator.parseAccountType("ADMIN"));
        assertNull(InputValidator.parseAccountType("invalid"));
    }

    @Test
    void testParsePaymentMethod() {
        assertEquals(PaymentMethod.CASH, InputValidator.parsePaymentMethod("CASH"));
        assertNull(InputValidator.parsePaymentMethod("invalid"));
    }
    
}
