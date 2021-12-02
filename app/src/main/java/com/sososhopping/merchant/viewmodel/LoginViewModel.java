package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.LoginRequestDto;
import com.sososhopping.merchant.model.auth.dto.response.LoginResponseDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

import java.util.function.BiConsumer;

public class LoginViewModel extends ViewModel {

    private final ObservableField<String> email = new ObservableField<>();
    private final ObservableField<String> password = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void requestLogin(BiConsumer<LoginRequestDto, LoginResponseDto> onSuccess,
                             Runnable onFailed,
                             Runnable onError) {
        AuthRepository.getInstance().requestLogin(this.toLoginRequestDto(), onSuccess, onFailed, onError);
    }

    private LoginRequestDto toLoginRequestDto() {
        return new LoginRequestDto(email.get(), password.get());
    }
}
