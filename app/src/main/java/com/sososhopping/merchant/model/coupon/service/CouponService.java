package com.sososhopping.merchant.model.coupon.service;

import com.sososhopping.merchant.model.coupon.dto.request.CouponModifyRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponRegisterRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateBeingRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateExpectedRequestDto;
import com.sososhopping.merchant.model.coupon.dto.response.CouponCheckResponseDto;
import com.sososhopping.merchant.model.coupon.dto.response.CouponListResponseDto;
import com.sososhopping.merchant.model.coupon.entity.Coupon;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CouponService {

    @GET("store/{storeId}/coupon")
    Call<CouponListResponseDto> requestCouponList(@Header("token") String token, @Path("storeId") int storeId);

    @GET("store/{storeId}/coupon/{couponId}")
    Call<Coupon> requestCoupon(@Header("token") String token, @Path("storeId") int storeId, @Path("couponId") int couponId);

    @POST("store/{storeId}/coupon")
    Call<Void> requestCouponRegister(@Header("token") String token, @Path("storeId") int storeId, @Body CouponRegisterRequestDto dto);

    @PATCH("store/{storeId}/coupon/{couponId}")
    Call<Void> requestCouponUpdateExpected(@Header("token") String token, @Path("storeId") int storeId, @Path("couponId") int couponId, @Body CouponUpdateExpectedRequestDto dto);

    @PATCH("store/{storeId}/coupon/{couponId}")
    Call<Void> requestCouponUpdateBeing(@Header("token") String token, @Path("storeId") int storeId, @Path("couponId") int couponId, @Body CouponUpdateBeingRequestDto dto);

    @DELETE("store/{storeId}/coupon/{couponId}")
    Call<Void> requestCouponDelete(@Header("token") String token, @Path("storeId") int storeId, @Path("couponId") int couponId);

    @GET("store/{storeId}/coupon/local")
    Call<CouponCheckResponseDto> requestCouponCheck(@Header("token") String token, @Path("storeId") int storeId, @Query("userPhone") String userPhone, @Query("couponCode") String couponCode);

    @POST("store/{storeId}/coupon/local")
    Call<Void> requestCouponModify(@Header("token") String token, @Path("storeId") int storeId, @Body CouponModifyRequestDto dto);
}
