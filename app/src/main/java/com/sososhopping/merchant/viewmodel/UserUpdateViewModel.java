package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class UserUpdateViewModel extends ViewModel {


    private final ObservableField<String> email = new ObservableField<>();
    private final ObservableField<String> name = new ObservableField<>();
    private final ObservableField<String> phone = new ObservableField<>();
    private final ObservableField<String> passwordCheck = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public ObservableField<String> getPasswordCheck() {
        return passwordCheck;
    }
}
