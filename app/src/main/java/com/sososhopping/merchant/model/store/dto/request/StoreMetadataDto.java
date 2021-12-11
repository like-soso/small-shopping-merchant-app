package com.sososhopping.merchant.model.store.dto.request;

import com.google.gson.annotations.SerializedName;

public class StoreMetadataDto {

    @SerializedName("businessNumber")
    String businessNumber;
    @SerializedName("representativeName")
    String representativeName;
    @SerializedName("businessName")
    String businessName;
    @SerializedName("openingDate")
    String openingDate;

    public StoreMetadataDto(String businessNumber, String representativeName, String businessName, String openingDate) {
        this.businessNumber = businessNumber;
        this.representativeName = representativeName;
        this.businessName = businessName;
        this.openingDate = openingDate;
    }
}
