package com.sososhopping.merchant.model.auth.dto.request;

public class PasswordChangeRequestDto {
    String email;
    String password;

    public PasswordChangeRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
