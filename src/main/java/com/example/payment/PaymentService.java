package com.example.payment;

public class PaymentService {
    public boolean processPayment(Payment payment) {
        if (!PaymentValidator.isValid(payment)) {
            throw new IllegalArgumentException("Invalid payment details");
        }
        System.out.println("✅ Payment processed securely: $" + payment.getAmount() + " " + payment.getCurrency());
        return true;
    }
}