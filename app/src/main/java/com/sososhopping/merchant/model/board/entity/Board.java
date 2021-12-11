package com.sososhopping.merchant.model.board.entity;

import com.google.gson.annotations.SerializedName;

public class Board {

    @SerializedName("id")
    int id;
    @SerializedName("storeId")
    int storeId;
    @SerializedName("title")
    String title;
    @SerializedName("content")
    String content;
    @SerializedName("writingType")
    String writingType;
    @SerializedName("imgUrl")
    String imgUrl;
    @SerializedName("createdAt")
    String createdAt;

    public int getId() {
        return id;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWritingType() {
        return writingType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
