package com.sososhopping.merchant.model.auth.dto.request;

import com.google.gson.annotations.SerializedName;

public class SignupRequestDto {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("name")
    String name;

    @SerializedName("phone")
    String phone;

    public SignupRequestDto(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
