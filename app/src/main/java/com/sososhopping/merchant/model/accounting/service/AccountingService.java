package com.sososhopping.merchant.model.accounting.service;

import com.sososhopping.merchant.model.accounting.dto.request.AccountingRegisterAndUpdateRequestDto;
import com.sososhopping.merchant.model.accounting.entity.Accounting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountingService {

    @GET("store/{storeId}/accounting")
    Call<List<Accounting>> requestAccountingList(@Header("token") String token, @Path("storeId") int storeId, @Query("yearMonth") String yearMonth);

    @GET("store/{storeId}/accounting/{accountingId}")
    Call<Accounting> requestAccountingItem(@Header("token") String token, @Path("storeId") int storeId, @Path("accountingId") int accountingId);

    @POST("store/{storeId}/accounting")
    Call<Void> requestAccountingRegister(@Header("token") String token, @Path("storeId") int storeId, @Body AccountingRegisterAndUpdateRequestDto dto);

    @PATCH("store/{storeId}/accounting/{accountingId}")
    Call<Void> requestAccountingUpdate(@Header("token") String token, @Path("storeId") int storeId, @Path("accountingId") int accountingId, @Body AccountingRegisterAndUpdateRequestDto dto);

    @DELETE("store/{storeId}/accounting/{accountingId}")
    Call<Void> requestAccountingDelete(@Header("token") String token, @Path("storeId") int storeId, @Path("accountingId") int accountingId);
}
