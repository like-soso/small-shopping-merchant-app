package com.sososhopping.merchant.model.store.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sososhopping.merchant.model.store.entity.StoreList;
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

    public void requestStoreList(Consumer<List<StoreList>> onSuccess, Runnable onError) {
        service.requestStoreList(TokenStore.getAuthToken()).enqueue(new Callback<List<StoreList>>() {
            @Override
            public void onResponse(Call<List<StoreList>> call, Response<List<StoreList>> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<List<StoreList>> call, Throwable t) {
                onError.run();
            }
        });
    }
}
