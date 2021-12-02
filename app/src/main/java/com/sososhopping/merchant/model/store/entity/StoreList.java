package com.sososhopping.merchant.model.store.entity;

import com.google.gson.annotations.SerializedName;

public class StoreList {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("imgUrl")
    String imgUrl;
    @SerializedName("description")
    String description;
    @SerializedName("storeStatus")
    String storeStatus;

    public StoreList(int id, String name, String imgUrl, String description, String status) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.storeStatus = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getStoreStatus() {
        return storeStatus;
    }
}
