package com.sososhopping.merchant.model.store.service;

import com.sososhopping.merchant.model.store.dto.response.StoreOpenStatusResponseDto;
import com.sososhopping.merchant.model.store.entity.StoreBrief;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface StoreService {

    @GET("store")
    Call<List<StoreBrief>> requestStoreList(@Header("token") String token);

    @GET("store/{storeId}/businessstatus")
    Call<StoreOpenStatusResponseDto> requestStoreOpenStatus(@Header("token") String token, @Path("storeId") int StoreId);

    @PATCH("store/{storeId}/businessstatus")
    Call<StoreOpenStatusResponseDto> requestStoreOpenStatusChange(@Header("token") String token, @Path("storeId") int StoreId);
}
