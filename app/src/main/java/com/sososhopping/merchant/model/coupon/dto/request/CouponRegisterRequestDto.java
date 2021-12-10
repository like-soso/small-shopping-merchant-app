package com.sososhopping.merchant.model.coupon.dto.request;

public class CouponRegisterRequestDto {

    String couponName;
    int stockQuantity;
    int minimumOrderPrice;
    String issuedStartDate;
    String issuedDueDate;
    String expiryDate;
    String couponType;
    double rateAmount;
    int fixAmount;

    public CouponRegisterRequestDto(String couponName, int stockQuantity, int minimumOrderPrice, String issuedStartDate, String issuedDueDate, String expiryDate, String couponType, double rateAmount, int fixAmount) {
        this.couponName = couponName;
        this.stockQuantity = stockQuantity;
        this.minimumOrderPrice = minimumOrderPrice;
        this.issuedStartDate = issuedStartDate;
        this.issuedDueDate = issuedDueDate;
        this.expiryDate = expiryDate;
        this.couponType = couponType;
        this.rateAmount = rateAmount;
        this.fixAmount = fixAmount;
    }
}
