package com.sososhopping.merchant.model.order.dto.request;

public class OrderProcessRequestDto {

    String type;

    private OrderProcessRequestDto(String type) {
        this.type = type;
    }

    public static OrderProcessRequestDto of(String type) {
        return new OrderProcessRequestDto(type);
    }
}
