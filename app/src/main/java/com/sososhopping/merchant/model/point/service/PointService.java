package com.sososhopping.merchant.model.point.service;

import com.sososhopping.merchant.model.point.dto.request.PointModifyRequestDto;
import com.sososhopping.merchant.model.point.dto.request.PointRuleRequestDto;
import com.sososhopping.merchant.model.point.dto.response.PointCheckResponseDto;
import com.sososhopping.merchant.model.point.dto.response.PointRuleResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PointService {

    @GET("store/{storeId}/pointpolicy")
    Call<PointRuleResponseDto> requestPointRule(@Header("token") String token, @Path("storeId") int storeId);

    @PATCH("store/{storeId}/pointpolicy")
    Call<Void> requestPointRuleUpdate(@Header("token") String token, @Path("storeId") int storeId, @Body PointRuleRequestDto dto);

    @GET("store/{storeId}/point/local")
    Call<PointCheckResponseDto> requestPointCheck(@Header("token") String token, @Path("storeId") int storeId, @Query("userPhone") String userPhone);

    @POST("store/{storeId}/point/local")
    Call<Void> requestPointUpdate(@Header("token") String token, @Path("storeId") int storeId, @Body PointModifyRequestDto dto);
}
