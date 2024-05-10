package com.example.coco;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTestCocoCareTest {
    //register
    @Test
    public void testIsValidEmail() {
        Register register = new Register();

        assertTrue(register.isValidEmail("test@gmail.com"));
        assertTrue(register.isValidEmail("user123@gmail.com"));

        assertFalse(register.isValidEmail("invalid_email.com"));
        assertFalse(register.isValidEmail("user@"));
        assertFalse(register.isValidEmail("user@example"));
    }

    @Test
    public void testIsValidSriLankanPhone() {
        Register register = new Register();
        assertTrue(register.isValidSriLankanPhone("0712345678"));
        assertTrue(register.isValidSriLankanPhone("0723456789"));

        assertFalse(register.isValidSriLankanPhone("0112345678"));
        assertFalse(register.isValidSriLankanPhone("07654321"));
        assertFalse(register.isValidSriLankanPhone("07123456789"));
    }
}
