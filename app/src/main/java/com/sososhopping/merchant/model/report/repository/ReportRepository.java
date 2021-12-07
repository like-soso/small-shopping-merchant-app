package com.sososhopping.merchant.model.report.repository;

import com.sososhopping.merchant.model.report.dto.request.UserReportRequestDto;
import com.sososhopping.merchant.model.report.service.ReportService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRepository {

    private final ReportService service;
    private static ReportRepository instance;

    private ReportRepository() {
        this.service = ApiServiceFactory.createService(ReportService.class);
    }

    public static synchronized ReportRepository getInstance() {
        if (instance == null) {
            instance = new ReportRepository();
        }

        return instance;
    }

    public void requestReport(int storeId, UserReportRequestDto dto, Runnable onSuccess) {
        service.requestReportUser(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) onSuccess.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ;
            }
        });
    }
}
