// src/main/java/com/example/payment/PaymentValidator.java
package com.example.payment;
// Add these lines at the start of PaymentValidator.java
import java.time.Year;
import java.time.Month;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentValidator {

    public static boolean isValid(Payment payment) {
        return isValidCardNumber(payment.getCardNumber())
                && isValidAmount(payment.getAmount())
                && isValidCurrency(payment.getCurrency())
                && isValidExpiryDate(payment.getExpiryDate());
    }

    private static boolean isValidCardNumber(String cardNumber) {
        // Luhn algorithm + basic length check (13–19 digits)
        if (cardNumber == null || !cardNumber.matches("\\d{13,19}")) {
            return false;
        }
        return luhnCheck(cardNumber);
    }

    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    private static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    private static boolean isValidCurrency(String currency) {
        // Basic ISO 4217 check (3 uppercase letters)
        return currency != null && currency.matches("[A-Z]{3}");
    }

    private static boolean isValidExpiryDate(String expiryDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            LocalDate date = LocalDate.parse(expiryDate, formatter);
            LocalDate now = LocalDate.now();
            LocalDate expiry = LocalDate.of(
                2000 + Integer.parseInt(expiryDate.substring(3)),
                Integer.parseInt(expiryDate.substring(0, 2)),
                1
            );
            return !expiry.isBefore(now);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}