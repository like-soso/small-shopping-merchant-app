package com.sososhopping.merchant.model.coupon.dto.response;

import com.sososhopping.merchant.model.coupon.entity.Coupon;

public class CouponCheckResponseDto {

    String userName;
    Coupon coupon;

    public String getUserName() {
        return userName;
    }

    public Coupon getCoupon() {
        return coupon;
    }
}
