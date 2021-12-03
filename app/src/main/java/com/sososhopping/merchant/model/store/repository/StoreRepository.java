package com.sososhopping.merchant.model.store.repository;


import androidx.annotation.NonNull;

import com.sososhopping.merchant.model.store.dto.response.StoreOpenStatusResponseDto;
import com.sososhopping.merchant.model.store.entity.StoreBrief;
import com.sososhopping.merchant.model.store.service.StoreService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRepository {
    private static StoreRepository instance;
    private final StoreService service;

    private StoreRepository() {
        service = ApiServiceFactory.createService(StoreService.class);
    }

    public static synchronized StoreRepository getInstance() {
        if(instance == null) {
            instance = new StoreRepository();
        }

        return instance;
    }

    public void requestStoreList(Consumer<List<StoreBrief>> onSuccess, Runnable onError) {
        service.requestStoreList(TokenStore.getAuthToken()).enqueue(new Callback<List<StoreBrief>>() {
            @Override
            public void onResponse(@NonNull Call<List<StoreBrief>> call, @NonNull Response<List<StoreBrief>> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StoreBrief>> call, @NonNull Throwable t) {
                onError.run();
            }
        });
    }

    public void requestStoreBusinessStatus(int storeId, Consumer<StoreOpenStatusResponseDto> consumer, Runnable onError) {
        service.requestStoreOpenStatus(TokenStore.getAuthToken(), storeId).enqueue(new Callback<StoreOpenStatusResponseDto>() {
            @Override
            public void onResponse(Call<StoreOpenStatusResponseDto> call, Response<StoreOpenStatusResponseDto> response) {
                consumer.accept(response.body());
            }

            @Override
            public void onFailure(Call<StoreOpenStatusResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestStoreBusinessStatusUpdate(int storeId, Consumer<StoreOpenStatusResponseDto> consumer, Runnable onError) {
        service.requestStoreOpenStatusChange(TokenStore.getAuthToken(), storeId).enqueue(new Callback<StoreOpenStatusResponseDto>() {
            @Override
            public void onResponse(Call<StoreOpenStatusResponseDto> call, Response<StoreOpenStatusResponseDto> response) {
                consumer.accept(response.body());
            }

            @Override
            public void onFailure(Call<StoreOpenStatusResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }
}
