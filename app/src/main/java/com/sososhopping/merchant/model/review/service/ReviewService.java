package com.sososhopping.merchant.model.review.service;

import com.sososhopping.merchant.model.review.dto.response.ReviewListResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ReviewService {

    @GET("store/{storeId}/review")
    Call<ReviewListResponseDto> requestReviewList(@Header("token") String token, @Path("storeId") int storeId);
}
