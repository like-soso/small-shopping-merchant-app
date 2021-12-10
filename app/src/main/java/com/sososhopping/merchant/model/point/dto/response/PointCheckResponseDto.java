package com.sososhopping.merchant.model.point.dto.response;

import com.google.gson.annotations.SerializedName;

public class PointCheckResponseDto {

    @SerializedName("userName")
    String userName;
    @SerializedName("userPoint")
    int amount;

    public String getUserName() {
        return userName;
    }

    public int getAmount() {
        return amount;
    }
}
