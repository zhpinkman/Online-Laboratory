package com.example.demo.domain.utility;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<ReceiptItem> receiptItems = new ArrayList<>();
    private double totalAmount;

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void addToReceipt(ReceiptItem receiptItem) {
        receiptItems.add(receiptItem);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }
}
