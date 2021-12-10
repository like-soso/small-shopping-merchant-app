package com.sososhopping.merchant.model.point.dto.request;

import com.google.gson.annotations.SerializedName;

public class PointModifyRequestDto {

    @SerializedName("userPhone")
    String userPhone;
    @SerializedName("pointAmount")
    int amount;
    @SerializedName("isSave")
    boolean isSave;

    public PointModifyRequestDto(String userPhone, int amount, boolean isSave) {
        this.userPhone = userPhone;
        this.amount = amount;
        this.isSave = isSave;
    }
}
