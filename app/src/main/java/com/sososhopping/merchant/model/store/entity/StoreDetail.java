package com.sososhopping.merchant.model.store.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreDetail {

    @SerializedName("id")
    int id;
    String imgUrl;
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
    @SerializedName("deliveryCharge")
    int deliveryCharge;
    @SerializedName("storeBusinessDays")
    List<StoreBusinessDay> storeBusinessDays;

    public StoreDetail(int id, String imgUrl, String name, String description, String phone, String storeType, String extraBusinessDay, Boolean localCurrencyStatus, Boolean deliveryStatus, String streetAddress, String detailedAddress, int deliveryCharge, List<StoreBusinessDay> storeBusinessDays) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.storeType = storeType;
        this.extraBusinessDay = extraBusinessDay;
        this.localCurrencyStatus = localCurrencyStatus;
        this.deliveryStatus = deliveryStatus;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.deliveryCharge = deliveryCharge;
        this.storeBusinessDays = storeBusinessDays;
    }

    public int getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getExtraBusinessDay() {
        return extraBusinessDay;
    }

    public Boolean getLocalCurrencyStatus() {
        return localCurrencyStatus;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public List<StoreBusinessDay> getStoreBusinessDays() {
        return storeBusinessDays;
    }
}
