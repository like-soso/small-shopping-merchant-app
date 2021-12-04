package com.sososhopping.merchant.model.order.dto.response;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.merchant.model.order.entity.Order;

import java.util.List;

public class OrderListResponseDto {

    @SerializedName("results")
    List<Order> orders;

    public OrderListResponseDto(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
