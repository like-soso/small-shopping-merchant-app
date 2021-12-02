package com.sososhopping.merchant.model.auth.dto.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDto {

    @SerializedName("token")
    String token;

    @SerializedName("firebaseToken")
    String firebaseToken;

    public String getToken() {
        return token;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }
}
