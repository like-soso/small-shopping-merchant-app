package com.sososhopping.merchant.model.auth.dto.response;

public class ProfileResponseDto {

    String email;
    String name;
    String phone;

    public ProfileResponseDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
