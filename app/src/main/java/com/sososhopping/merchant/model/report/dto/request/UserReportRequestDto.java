package com.sososhopping.merchant.model.report.dto.request;

public class UserReportRequestDto {

    int userId;
    String userName;
    String content;

    public UserReportRequestDto(int userId, String userName, String content) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
    }
}
