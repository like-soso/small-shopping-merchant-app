package com.sososhopping.merchant.model.store.dto.response;

import com.google.gson.annotations.SerializedName;

public class StoreOpenStatusResponseDto {

    @SerializedName("businessStatus")
    boolean openStatus;

    public StoreOpenStatusResponseDto(boolean openStatus) {
        this.openStatus = openStatus;
    }

    public boolean getOpenStatus() {
        return openStatus;
    }
}
