package com.sososhopping.merchant.model.auth.dto.request;

public class PasswordFindRequestDto {
    String email;
    String name;
    String phone;

    public PasswordFindRequestDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
