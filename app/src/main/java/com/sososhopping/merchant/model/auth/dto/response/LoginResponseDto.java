package com.sososhopping.merchant.model.auth.dto.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDto {

    //TODO: 파이어베이스 토큰 관련 백엔드 확인

    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }
}
