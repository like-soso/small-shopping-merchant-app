package com.sososhopping.merchant.model.order.entity;

public class OrderItem {
    String name;
    String description;
    int quantity;
    int totalPrice;

    public OrderItem(String name, String description, int quantity, int totalPrice) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
