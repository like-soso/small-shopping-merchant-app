package com.sososhopping.merchant.model.auth.service;

import com.sososhopping.merchant.model.auth.dto.request.EmailDuplicationCheckRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.EmailFindRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.LoginRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordChangeRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordFindRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.SignupRequestDto;
import com.sososhopping.merchant.model.auth.dto.response.EmailFindResponseDto;
import com.sososhopping.merchant.model.auth.dto.response.LoginResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/signup")
    Call<Void> requestSignup(@Body SignupRequestDto dto);

    @POST("auth/signup/validation")
    Call<Void> requestEmailDuplicationCheck(@Body EmailDuplicationCheckRequestDto dto);

    @POST("auth/login")
    Call<LoginResponseDto> requestLogin(@Body LoginRequestDto dto);

    @POST("auth/findEmail")
    Call<EmailFindResponseDto> requestFindEmail(@Body EmailFindRequestDto dto);

    @POST("auth/findPassword")
    Call<Void> requestFindPassword(@Body PasswordFindRequestDto dto);

    @POST("auth/changePassword")
    Call<Void> requestChangePassword(@Body PasswordChangeRequestDto dto);
}
