package com.example.demo.domain;

public class PaymentReceipt {
    private String code;
    private double price;

    public PaymentReceipt(double amountToPay) {
        this.price = amountToPay;
    }
}
