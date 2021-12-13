package com.sososhopping.merchant.model.auth.dto.request;

public class EmailFindRequestDto {
    String name;
    String phone;

    public EmailFindRequestDto(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
