package com.sososhopping.merchant.model.auth.dto.request;

import com.google.gson.annotations.SerializedName;

public class EmailDuplicationCheckRequestDto {

    @SerializedName("email")
    String email;

    public EmailDuplicationCheckRequestDto(String email) {
        this.email = email;
    }
}
