package com.sososhopping.merchant.model.coupon.entity;

public class Coupon {

    int id;
    int storeId;
    String storeName;
    String couponName;
    int stockQuantity;
    String couponCode;
    int minimumOrderPrice;
    String issuedStartDate;
    String issuedDueDate;
    String expiryDate;
    String couponType;
    double rateAmount;
    int fixAmount;

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCouponName() {
        return couponName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public int getMinimumOrderPrice() {
        return minimumOrderPrice;
    }

    public String getIssuedStartDate() {
        return issuedStartDate;
    }

    public String getIssuedDueDate() {
        return issuedDueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCouponType() {
        return couponType;
    }

    public double getRateAmount() {
        return rateAmount;
    }

    public int getFixAmount() {
        return fixAmount;
    }
}
