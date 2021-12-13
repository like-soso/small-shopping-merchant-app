package com.sososhopping.merchant.model.coupon.dto.response;

import com.sososhopping.merchant.model.coupon.entity.Coupon;

public class CouponCheckResponseDto {

    String userNickname;
    Coupon coupon;

    public String getUserName() {
        return userNickname;
    }

    public Coupon getCoupon() {
        return coupon;
    }
}
