package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class EmailFindViewModel extends ViewModel {

    private final ObservableField<String> name = new ObservableField<>();
    private final ObservableField<String> phone = new ObservableField<>();

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }
}
