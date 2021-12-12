package com.sososhopping.merchant.model.auth.repository;

import androidx.annotation.NonNull;

import com.sososhopping.merchant.model.auth.dto.request.EmailDuplicationCheckRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.EmailFindRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.LoginRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordChangeRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordFindRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordUpdateRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.SignupRequestDto;
import com.sososhopping.merchant.model.auth.dto.response.EmailFindResponseDto;
import com.sososhopping.merchant.model.auth.dto.response.LoginResponseDto;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.model.auth.service.AuthService;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                onFailed.run();
            }
        });
    }

    public void requestSignup(SignupRequestDto dto,
                              Runnable onSuccess,
                              Runnable onFailed){
        service.requestSignup(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == 201) onSuccess.run();
                else onFailed.run();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                onFailed.run();
            }
        });
    }

    public void requestLogin(LoginRequestDto dto,
                             BiConsumer<LoginRequestDto, LoginResponseDto> onSuccess,
                             Runnable onFailed,
                             Runnable onError){
        service.requestLogin(dto).enqueue(new Callback<LoginResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseDto> call, @NonNull Response<LoginResponseDto> response) {
                if(response.code() == 200) {
                    onSuccess.accept(dto ,response.body());
                } else {
                    onFailed.run();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseDto> call, @NonNull Throwable t) {
                onError.run();
            }
        });
    }

    public void requestEmailFind(EmailFindRequestDto dto,
                                 Consumer<EmailFindResponseDto> onSuccess,
                                 Runnable onInvalid,
                                 Runnable onError) {
        service.requestFindEmail(dto).enqueue(new Callback<EmailFindResponseDto>() {
            @Override
            public void onResponse(Call<EmailFindResponseDto> call, Response<EmailFindResponseDto> response) {
                if (response.code() == 200) onSuccess.accept(response.body());
                else if (response.code() == 404) onInvalid.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<EmailFindResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestPasswordFind(PasswordFindRequestDto toFindDto, Runnable onSuccess, Runnable onInvalid, Runnable onError) {
        service.requestFindPassword(toFindDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.run();
                else if (response.code() == 404) onInvalid.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestPasswordChange(PasswordChangeRequestDto dto, Runnable onSuccess, Runnable onError) {
        service.requestChangePassword(dto).enqueue(new Callback<Void>() {
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

    public void requestPasswordUpdate(PasswordUpdateRequestDto dto, Runnable onSuccess, Runnable onInvalid, Runnable onError) {
        service.requestUpdatePassword(TokenStore.getAuthToken(), dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.run();
                else if (response.code() == 401) onInvalid.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }
}
