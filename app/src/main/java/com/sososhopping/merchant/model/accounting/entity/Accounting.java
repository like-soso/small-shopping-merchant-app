package com.sososhopping.merchant.model.accounting.entity;

import com.google.gson.annotations.SerializedName;

public class Accounting {

    @SerializedName("id")
    int id;
    @SerializedName("storeId")
    int storeId;
    @SerializedName("amount")
    int amount;
    @SerializedName("description")
    String memo;
    @SerializedName("date")
    String dateTime;

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }

    public String getDateTime() {
        return dateTime;
    }
}
