package com.sososhopping.merchant.model.coupon.dto.request;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CouponUpdateBeingRequestDto {

    String issuedDueDate;

    public CouponUpdateBeingRequestDto(String issuedDueDate) {
        this.issuedDueDate = issuedDueDate;
    }

    public static CouponUpdateBeingRequestDto toSuspend() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        String date = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        return new CouponUpdateBeingRequestDto(date);
    }
}
