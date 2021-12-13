package com.sososhopping.merchant.model.auth.dto.request;

public class ProfileUpdateRequestDto {

    String name;
    String phone;
    String password;

    public ProfileUpdateRequestDto(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }
}
