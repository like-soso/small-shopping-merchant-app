package com.sososhopping.merchant.model.item.dto.request;

import com.google.gson.annotations.SerializedName;

public class ItemUpdateRequestDto {

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("purchaseUnit")
    String purchaseUnit;
    @SerializedName("price")
    Integer price;
    @SerializedName("saleStatus")
    boolean salesStatus;

    public ItemUpdateRequestDto(String name, String description, String purchaseUnit, Integer price, boolean salesStatus) {
        this.name = name;
        this.description = description;
        this.purchaseUnit = purchaseUnit;
        this.price = price;
        this.salesStatus = salesStatus;
    }
}
