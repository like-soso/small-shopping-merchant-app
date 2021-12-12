package com.sososhopping.merchant.model.point.repository;

import com.sososhopping.merchant.model.point.dto.request.PointModifyRequestDto;
import com.sososhopping.merchant.model.point.dto.request.PointRuleRequestDto;
import com.sososhopping.merchant.model.point.dto.response.PointCheckResponseDto;
import com.sososhopping.merchant.model.point.dto.response.PointRuleResponseDto;
import com.sososhopping.merchant.model.point.service.PointService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointRepository {

    private static PointRepository instance;
    private final PointService service;

    private PointRepository() {
        this.service = ApiServiceFactory.createService(PointService.class);
    }

    public static synchronized PointRepository getInstance() {
        if(instance == null) {
            instance = new PointRepository();
        }

        return instance;
    }

    public void requestPointRule(int storeId, Consumer<PointRuleResponseDto> onSuccess, Runnable onError) {
        service.requestPointRule(TokenStore.getAuthToken(), storeId).enqueue(new Callback<PointRuleResponseDto>() {
            @Override
            public void onResponse(Call<PointRuleResponseDto> call, Response<PointRuleResponseDto> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<PointRuleResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestPointRuleUpdate(int storeId, PointRuleRequestDto dto, Runnable onSuccess, Runnable onError) {
        service.requestPointRuleUpdate(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestPointCheck(int storeId, String userPhone, Consumer<PointCheckResponseDto> onSuccess, Runnable onFailed, Runnable onError) {
        System.out.println(userPhone);
        service.requestPointCheck(TokenStore.getAuthToken(), storeId, userPhone).enqueue(new Callback<PointCheckResponseDto>() {
            @Override
            public void onResponse(Call<PointCheckResponseDto> call, Response<PointCheckResponseDto> response) {
                System.out.println(response.code());
                if (response.code() == 200) onSuccess.accept(response.body());
                else if (response.code() == 400) onFailed.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<PointCheckResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestPointModify(int storeId, PointModifyRequestDto dto, Runnable onSuccess, Runnable onError) {
        service.requestPointUpdate(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    onSuccess.run();
                } else onError.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }
}
