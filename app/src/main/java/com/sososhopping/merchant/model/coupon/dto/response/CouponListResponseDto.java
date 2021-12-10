package com.sososhopping.merchant.model.coupon.dto.response;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.merchant.model.coupon.entity.Coupon;

import java.util.List;

public class CouponListResponseDto {

    @SerializedName("expected")
    List<Coupon> expected;
    @SerializedName("being")
    List<Coupon> being;

    public List<Coupon> getExpected() {
        return expected;
    }

    public List<Coupon> getBeing() {
        return being;
    }
}
