package com.sososhopping.merchant.model.order.entity;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("itemName")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("totalPrice")
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
