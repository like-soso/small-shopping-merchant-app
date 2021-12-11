package com.sososhopping.merchant.model.store.dto.request;

import com.google.gson.annotations.SerializedName;

public class StoreBusinessDaysDto {

    @SerializedName("day")
    String day;
    @SerializedName("isOpen")
    Boolean isOpen;
    @SerializedName("openTime")
    String openTime;
    @SerializedName("closeTime")
    String closeTime;

    public StoreBusinessDaysDto(String day, Boolean isOpen, String openTime, String closeTime) {
        this.day = day;
        this.isOpen = isOpen;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
