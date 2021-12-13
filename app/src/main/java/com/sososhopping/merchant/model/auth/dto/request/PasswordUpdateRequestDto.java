package com.sososhopping.merchant.model.auth.dto.request;

public class PasswordUpdateRequestDto {

    String password;
    String newPassword;

    public PasswordUpdateRequestDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
