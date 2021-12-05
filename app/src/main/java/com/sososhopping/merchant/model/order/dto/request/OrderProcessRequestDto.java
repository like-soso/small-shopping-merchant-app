package com.sososhopping.merchant.model.order.dto.request;

import com.google.gson.annotations.SerializedName;

public class OrderProcessRequestDto {

    @SerializedName("action")
    String type;

    private OrderProcessRequestDto(String type) {
        this.type = type;
    }

    public static OrderProcessRequestDto of(String type) {
        return new OrderProcessRequestDto(type);
    }
}
