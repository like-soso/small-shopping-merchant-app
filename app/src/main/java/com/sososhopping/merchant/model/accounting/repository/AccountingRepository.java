package com.sososhopping.merchant.model.accounting.repository;

import com.sososhopping.merchant.model.accounting.dto.request.AccountingRegisterAndUpdateRequestDto;
import com.sososhopping.merchant.model.accounting.entity.Accounting;
import com.sososhopping.merchant.model.accounting.service.AccountingService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountingRepository {
    private static AccountingRepository instance;
    private final AccountingService service;

    private AccountingRepository() {
        this.service = ApiServiceFactory.createService(AccountingService.class);
    }

    public static synchronized AccountingRepository getInstance() {
        if(instance == null) {
            instance = new AccountingRepository();
        }

        return instance;
    }

    public void requestAccountingList(int storeId, String yearMonth, Consumer<List<Accounting>> consumer, Runnable onError) {
        service.requestAccountingList(TokenStore.getAuthToken(), storeId, yearMonth).enqueue(new Callback<List<Accounting>>() {
            public void onResponse(Call<List<Accounting>> call, Response<List<Accounting>> response) {
                System.out.println(response.code());
                if(response.code() == 200) {
                    consumer.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<List<Accounting>> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestAccountingRegister(int storeId,
                                          AccountingRegisterAndUpdateRequestDto dto,
                                          Runnable onSuccess,
                                          Runnable onError) {
        service.requestAccountingRegister(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                if(response.code() == 201) {
                    onSuccess.run();
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestAccountingDelete(int storeId,
                                        int accountingId,
                                        int position,
                                        Consumer<Integer> onSuccess) {
        service.requestAccountingDelete(TokenStore.getAuthToken(), storeId, accountingId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                if (response.code() == 200) onSuccess.accept(position);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
