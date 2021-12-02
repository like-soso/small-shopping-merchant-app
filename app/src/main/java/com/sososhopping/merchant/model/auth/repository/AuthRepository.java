package com.sososhopping.merchant.model.auth.repository;

import com.sososhopping.merchant.model.auth.dto.request.EmailDuplicationCheckRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.SignupRequestDto;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.model.auth.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private static AuthRepository instance;
    private final AuthService service;

    private AuthRepository() {
        service = ApiServiceFactory.createService(AuthService.class);
    }

    public static synchronized AuthRepository getInstance() {
        if(instance == null) {
            instance = new AuthRepository();
        }

        return instance;
    }

    public void requestEmailDuplicationCheck(EmailDuplicationCheckRequestDto dto,
                                             Runnable onNotDuplicated,
                                             Runnable onDuplicated,
                                             Runnable onFailed) {
        service.requestEmailDuplicationCheck(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        onNotDuplicated.run();
                        break;
                    case 409:
                        onDuplicated.run();
                        break;
                    default:
                        onFailed.run();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onFailed.run();
            }
        });
    }

    public void requestSignup(SignupRequestDto dto,
                              Runnable onSuccess,
                              Runnable onFailed){
        service.requestSignup(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) onSuccess.run();
                else onFailed.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onFailed.run();
            }
        });
    }
}
