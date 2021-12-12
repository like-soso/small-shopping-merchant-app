package com.sososhopping.merchant.model.store.dto.request;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.merchant.model.store.entity.StoreBusinessDay;

import java.util.List;

public class StoreUpdateRequestDto {

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("phone")
    String phone;
    @SerializedName("storeType")
    String storeType;
    @SerializedName("extraBusinessDay")
    String extraBusinessDay;
    @SerializedName("localCurrencyStatus")
    Boolean localCurrencyStatus;
    @SerializedName("deliveryStatus")
    Boolean deliveryStatus;
    @SerializedName("storeBusinessDays")
    List<StoreBusinessDay> storeBusinessDays;
    @SerializedName("deliveryCharge")
    int deliveryCharge;

    public StoreUpdateRequestDto(String name, String description, String phone, String storeType, String extraBusinessDay, Boolean localCurrencyStatus, Boolean deliveryStatus, List<StoreBusinessDay> storeBusinessDays, int deliveryCharge) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.storeType = storeType;
        this.extraBusinessDay = extraBusinessDay;
        this.localCurrencyStatus = localCurrencyStatus;
        this.deliveryStatus = deliveryStatus;
        this.storeBusinessDays = storeBusinessDays;
        this.deliveryCharge = deliveryCharge;
    }
}
