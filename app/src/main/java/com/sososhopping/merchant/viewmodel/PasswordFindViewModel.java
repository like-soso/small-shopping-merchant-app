package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.PasswordChangeRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.PasswordFindRequestDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

public class PasswordFindViewModel extends ViewModel {

    private final ObservableField<String> email = new ObservableField<>("");
    private final ObservableField<String> name = new ObservableField<>("");
    private final ObservableField<String> phone = new ObservableField<>("");
    private final ObservableField<String> password = new ObservableField<>("");
    private final ObservableField<String> passwordCheck = new ObservableField<>("");

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<String> getPasswordCheck() {
        return passwordCheck;
    }

    public void requestPasswordFind(Runnable onSuccess, Runnable onInvalid, Runnable onError) {
        AuthRepository.getInstance().requestPasswordFind(this.toFindDto(), onSuccess, onInvalid, onError);
    }

    public void requestPasswordChange(Runnable onSuccess, Runnable onError) {
        AuthRepository.getInstance().requestPasswordChange(this.toChangeDto(), onSuccess, onError);
    }

    public boolean validate(Runnable onValid, Runnable onInvalid, Runnable onPasswordTooShort) {
        boolean ret = (password.get().length() >= 8) && password.get().equals(passwordCheck.get());

        if (password.get().equals(passwordCheck.get())) {
            if (password.get().length() < 8) onPasswordTooShort.run();
            else onValid.run();
        }
        else onInvalid.run();

        return ret;
    }

    private PasswordFindRequestDto toFindDto() {
        return new PasswordFindRequestDto(email.get(), name.get(), phone.get());
    }

    private PasswordChangeRequestDto toChangeDto() {
        return new PasswordChangeRequestDto(email.get(), password.get());
    }
}
