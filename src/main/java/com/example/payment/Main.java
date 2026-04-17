package com.example.payment;

public class Main {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        Payment validPayment = new Payment("4111111111111111", 99.99, "USD", "12/28");
        try {
            service.processPayment(validPayment);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Payment failed: " + e.getMessage());
        }
    }
}