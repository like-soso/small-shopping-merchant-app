package com.sososhopping.merchant.model.coupon.dto.request;

public class CouponModifyRequestDto {

    String phone;
    String couponCode;

    public CouponModifyRequestDto(String userPhone, String couponCode) {
        this.phone = userPhone;
        this.couponCode = couponCode;
    }
}
