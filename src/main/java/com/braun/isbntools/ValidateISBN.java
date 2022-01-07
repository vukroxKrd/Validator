package com.braun.isbntools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateISBN {

    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;
    public static final int ISBN_10_DIGITS = 10;
    public static final int ISBN_13_DIGITS = 13;

    public static void main(String[] args) {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("123456789X");
        System.out.println(result);
    }

    public boolean checkISBN(String isbn) {

        boolean result = false;
        int number = 0;
        int total = 0;

        int length = isbn.length();
        if (length == ISBN_10_DIGITS || length == ISBN_13_DIGITS) {
            //just continue
            System.out.println("Number has either 10 or 13 symbols - continue. Next checking if symbols are numbers.");
        } else {
            throw new NumberFormatException("Provided isbn should contain 10 or 13 digits. Provide a correct input, please");
        }
        if (!isbnHasDigitsOnly(isbn)) {
            throw new NumberFormatException("Provided isbn contains not allowed symbols. Provide a correct input, please");
        }

        switch (length) {
            case ISBN_10_DIGITS:
                for (int i = 0; i < 10; i++) {
                    char numRep = isbn.charAt(i);
                    if (numRep == 'X' || numRep == 'x') {
                        number = number + 10;
                    } else {
                        number = Character.getNumericValue(numRep);
                    }
                    total += number * (10 - i);
                }
                result = total % SHORT_ISBN_MULTIPLIER == 0;
                System.out.println(result ? "ISBN " + isbn + " верный" : "ISBN " + isbn + " не верный");
                break;
            case ISBN_13_DIGITS:
                for (int i = 0, j = 1; i < 12; i++, j++) {
                    char numRep = isbn.charAt(i);
                    number = Character.getNumericValue(numRep);
                    if (j % 2 == 0) {
                        total += number * 3;
                    } else {
                        total += number * 1;
                    }
                }
                int lasNum = 10 - (total % LONG_ISBN_MULTIPLIER);
                System.out.println("Last digit in 13 digit ISBN is: " + lasNum);
                result = (lasNum == Character.getNumericValue(isbn.charAt(12)));
                break;
        }
        return result;
    }

    private boolean isbnHasDigitsOnly(String isbn) {
        boolean result = false;
        switch (isbn.length()) {
            case (10):
                String regex10Digits = "[0-9]{9}?[0-9||xX]";
                Pattern pattern10Dig = Pattern.compile(regex10Digits);
                Matcher matcher10Dig = pattern10Dig.matcher(isbn);
                result = matcher10Dig.matches();
                break;
            case (13):
                String regex13Digits = "[0-9]{12}?[0-9||xX]";
                Pattern pattern13Dig = Pattern.compile(regex13Digits);
                Matcher matcher13Dig = pattern13Dig.matcher(isbn);
                result = matcher13Dig.matches();
                break;
        }
        return result;
    }
}
