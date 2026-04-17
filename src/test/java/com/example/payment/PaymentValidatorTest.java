package com.example.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentValidatorTest {
    @Test
    public void testValidPayment() {
        Payment validPayment = new Payment("4111111111111111", 100.0, "USD", "12/28");
        assertTrue(PaymentValidator.isValid(validPayment));
    }

    @Test
    public void testInvalidCardNumber() {
        Payment invalidCard = new Payment("1234567890", 50.0, "USD", "12/28");
        assertFalse(PaymentValidator.isValid(invalidCard));
    }

    @Test
    public void testInvalidAmount() {
        Payment invalidAmount = new Payment("4111111111111111", -10.0, "USD", "12/28");
        assertFalse(PaymentValidator.isValid(invalidAmount));
    }

    @Test
    public void testInvalidCurrency() {
        Payment invalidCurrency = new Payment("4111111111111111", 100.0, "EUR", "12/28");
        assertFalse(PaymentValidator.isValid(invalidCurrency));
    }

    @Test
    public void testExpiredCard() {
        Payment expired = new Payment("4111111111111111", 100.0, "USD", "01/20");
        assertFalse(PaymentValidator.isValid(expired));
    }
}