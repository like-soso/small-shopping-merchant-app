package com.sososhopping.merchant.model.report.service;

import com.sososhopping.merchant.model.report.dto.request.UserReportRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportService {

    @POST("store/{storeId}/report")
    Call<Void> requestReportUser(@Header("token") String token, @Path("storeId") int storeId, @Body UserReportRequestDto dto);
}
