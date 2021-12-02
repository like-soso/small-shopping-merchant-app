package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class PasswordUpdateViewModel extends ViewModel {

    private final ObservableField<String> password = new ObservableField<>();
    private final ObservableField<String> newPassword = new ObservableField<>();
    private final ObservableField<String> newPasswordCheck = new ObservableField<>();

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<String> getNewPassword() {
        return newPassword;
    }

    public ObservableField<String> getNewPasswordCheck() {
        return newPasswordCheck;
    }
}
