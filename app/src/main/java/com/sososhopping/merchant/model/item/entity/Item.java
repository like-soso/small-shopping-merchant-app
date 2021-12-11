package com.sososhopping.merchant.model.item.entity;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    int id;
    @SerializedName("storeId")
    int storeId;
    @SerializedName("imgUrl")
    String imgUrl;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("purchaseUnit")
    String purchaseUnit;
    @SerializedName("price")
    int unitPrice;
    @SerializedName("saleStatus")
    boolean saleStatus;

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
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

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public boolean isSaleStatus() {
        return saleStatus;
    }
}
