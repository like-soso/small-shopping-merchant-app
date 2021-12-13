package com.sososhopping.merchant.model.coupon.dto.response;

import com.sososhopping.merchant.model.coupon.entity.Coupon;

public class CouponCheckResponseDto {

    String userNickName;
    Coupon coupon;

    public String getUserName() {
        return userNickName;
    }

    public Coupon getCoupon() {
        return coupon;
    }
}
