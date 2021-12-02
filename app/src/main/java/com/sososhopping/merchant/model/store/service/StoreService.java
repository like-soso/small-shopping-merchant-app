package com.sososhopping.merchant.model.store.service;

import com.sososhopping.merchant.model.store.entity.StoreBrief;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StoreService {

    @GET("store")
    Call<List<StoreBrief>> requestStoreList(@Header("token") String token);
}
