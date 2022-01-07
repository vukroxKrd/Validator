package com.braun.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    @Test
    public void checkValid10DigitIsbn() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue("first value", result);
        result = validator.checkISBN("0140177396");
        assertTrue("second value", result);
    }

    @Test
    public void checkInvalid10DigitIsbn() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449113");
        assertFalse(result);
    }

    @Test
    public void checkValid13DigitIsbnTest() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9780306406157");
        assertTrue("First 13 digit value", result);
        result = validator.checkISBN("9781400079988");
        assertTrue("Second 13 digit value", result);
    }

    @Test
    public void checkInvalid13DigitIsbnTest() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781400079986");
        assertFalse(result);
    }

    @Test(expected = NumberFormatException.class)
    public void lessThan10DigitsIsbnsNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void notAllowedSymbols10DigitIsbnTest() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("HelloWorld");
    }

    @Test(expected = NumberFormatException.class)
    public void notAllowedSymbols13DigitIsbnTest() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("HelloWorldasd");
    }

    @Test
    public void allowedSymbolsAndLetterXinIsbnsTest() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("123456788X");
        assertFalse("Result with X invalid", result);
        boolean result2 = validator.checkISBN("0140449116");
        assertTrue("Result with numbers", result2);
        boolean result3 = validator.checkISBN("012000030X");
        assertTrue("Result with X valid", result3);
    }


}