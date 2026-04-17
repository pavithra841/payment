package com.example.payment;

public class Payment {
    private String cardNumber;
    private double amount;
    private String currency;
    private String expiryDate;

    public Payment(String cardNumber, double amount, String currency, String expiryDate) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() { return cardNumber; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getExpiryDate() { return expiryDate; }
}