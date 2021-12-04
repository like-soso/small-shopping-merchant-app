package com.sososhopping.merchant.model.order.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("orderId")
    String orderId;
    @SerializedName("userId")
    int userId;
    @SerializedName("ownerId")
    int storeId;
    @SerializedName("orterItems")
    List<OrderItem> orderItemList;
    @SerializedName("ordererName")
    String ordererName;
    @SerializedName("ordererPhone")
    String ordererPhone;
    @SerializedName("orderType")
    String orderType;
    @SerializedName("visitDate")
    String visitDate;
    @SerializedName("storeName")
    String storeName;
    @SerializedName("deliveryCharge")
    int deliveryCharge;
    @SerializedName("deliveryStreetAddress")
    String deliveryAddress;
    @SerializedName("deliveryDetailedAddress")
    String deliveryDetailedAddress;
    @SerializedName("orderPrice")
    int orderPrice;
    @SerializedName("usedPoint")
    int usedPoint;
    @SerializedName("couponDiscountPrice")
    int couponDiscountPrice;
    @SerializedName("finalPrice")
    int finalPrice;
    @SerializedName("createdAt")
    String createdAt;

    public Order(String orderId, int userId, int storeId, List<OrderItem> orderItemList, String ordererName, String ordererPhone, String orderType, String visitDate, String storeName, int deliveryCharge, String deliveryAddress, String deliveryDetailedAddress, int orderPrice, int usedPoint, int couponDiscountPrice, int finalPrice, String createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderItemList = orderItemList;
        this.ordererName = ordererName;
        this.ordererPhone = ordererPhone;
        this.orderType = orderType;
        this.visitDate = visitDate;
        this.storeName = storeName;
        this.deliveryCharge = deliveryCharge;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDetailedAddress = deliveryDetailedAddress;
        this.orderPrice = orderPrice;
        this.usedPoint = usedPoint;
        this.couponDiscountPrice = couponDiscountPrice;
        this.finalPrice = finalPrice;
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public String getOrdererPhone() {
        return ordererPhone;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryDetailedAddress() {
        return deliveryDetailedAddress;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public int getUsedPoint() {
        return usedPoint;
    }

    public int getCouponDiscountPrice() {
        return couponDiscountPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
