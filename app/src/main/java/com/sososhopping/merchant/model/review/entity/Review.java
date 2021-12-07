package com.sososhopping.merchant.model.review.entity;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("userId")
    int userId;
    @SerializedName("storeId")
    int storeId;
    @SerializedName("userName")
    String username;
    @SerializedName("score")
    double score;
    @SerializedName("content")
    String content;
    @SerializedName("createdAt")
    String createdAt;

    public int getUserId() {
        return userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
