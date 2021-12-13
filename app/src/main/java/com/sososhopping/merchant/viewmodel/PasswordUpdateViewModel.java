package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.PasswordUpdateRequestDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

public class PasswordUpdateViewModel extends ViewModel {

    private final ObservableField<String> password = new ObservableField<>("");
    private final ObservableField<String> newPassword = new ObservableField<>("");
    private final ObservableField<String> newPasswordCheck = new ObservableField<>("");

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<String> getNewPassword() {
        return newPassword;
    }

    public ObservableField<String> getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public boolean validate(Runnable onValid, Runnable onInvalid, Runnable onPasswordTooShort) {
        boolean ret = (newPassword.get().length() >= 8) && newPassword.get().equals(newPasswordCheck.get());

        if (newPassword.get().equals(newPasswordCheck.get())) {
            if (newPassword.get().length() < 8) onPasswordTooShort.run();
            else onValid.run();
        }
        else onInvalid.run();

        return ret;
    }

    public void requestPasswordUpdate(Runnable onSuccess, Runnable onInvalid, Runnable onError) {
        AuthRepository.getInstance().requestPasswordUpdate(this.toDto(), onSuccess, onInvalid, onError);
    }

    private PasswordUpdateRequestDto toDto() {
        return new PasswordUpdateRequestDto(password.get(), newPassword.get());
    }
}
