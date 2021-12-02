package com.sososhopping.merchant.model.auth.service;

import com.sososhopping.merchant.model.auth.dto.request.EmailDuplicationCheckRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.SignupRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/signup")
    Call<Void> requestSignup(@Body SignupRequestDto dto);

    @POST("auth/signup/validation")
    Call<Void> requestEmailDuplicationCheck(@Body EmailDuplicationCheckRequestDto dto);
}
