package com.sososhopping.merchant.model.board.dto.request;

import com.google.gson.annotations.SerializedName;

public class BoardRegisterRequestDto {

    @SerializedName("title")
    String title;
    @SerializedName("content")
    String content;
    @SerializedName("writingType")
    String writingType;

    public BoardRegisterRequestDto(String title, String content, String writingType) {
        this.title = title;
        this.content = content;
        this.writingType = writingType;
    }
}
