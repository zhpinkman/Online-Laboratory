package com.example.demo.domain.utility;

public class ReceiptItem {
    private String title;
    private double price;


    public ReceiptItem(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}
