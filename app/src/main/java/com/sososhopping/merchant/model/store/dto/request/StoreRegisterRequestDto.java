package com.sososhopping.merchant.model.store.dto.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreRegisterRequestDto {

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
    @SerializedName("streetAddress")
    String streetAddress;
    @SerializedName("detailedAddress")
    String detailedAddress;
    @SerializedName("lat")
    String lat;
    @SerializedName("lng")
    String lng;
    @SerializedName("storeBusinessDays")
    List<StoreBusinessDaysDto> storeBusinessDays;
    @SerializedName("storeMetaDataRequestDto")
    StoreMetadataDto storeMetaDataDto;
    @SerializedName("deliveryCharge")
    int deliveryCharge;

    public StoreRegisterRequestDto(String name, String description, String phone, String storeType, String extraBusinessDay, Boolean localCurrencyStatus, Boolean deliveryStatus, String streetAddress, String detailedAddress, String lat, String lng, List<StoreBusinessDaysDto> storeBusinessDays, StoreMetadataDto storeMetaDataDto, int deliveryCharge) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.storeType = storeType;
        this.extraBusinessDay = extraBusinessDay;
        this.localCurrencyStatus = localCurrencyStatus;
        this.deliveryStatus = deliveryStatus;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.lat = lat;
        this.lng = lng;
        this.storeBusinessDays = storeBusinessDays;
        this.storeMetaDataDto = storeMetaDataDto;
        this.deliveryCharge = deliveryCharge;
    }
}
