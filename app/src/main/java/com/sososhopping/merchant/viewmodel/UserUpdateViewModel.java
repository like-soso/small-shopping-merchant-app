package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.ProfileUpdateRequestDto;
import com.sososhopping.merchant.model.auth.dto.response.ProfileResponseDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

public class UserUpdateViewModel extends ViewModel {


    private final ObservableField<String> email = new ObservableField<>("");
    private final ObservableField<String> name = new ObservableField<>("");
    private final ObservableField<String> phone = new ObservableField<>("");
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

    public ObservableField<String> getPasswordCheck() {
        return passwordCheck;
    }

    public void requestUpdate(Runnable onSuccess, Runnable onInvalid, Runnable onPhoneDup, Runnable onError) {
        AuthRepository.getInstance().requestProfileUpdate(this.toDto(), onSuccess, onInvalid, onPhoneDup, onError);
    }

    private ProfileUpdateRequestDto toDto() {
        return new ProfileUpdateRequestDto(name.get(), phone.get(), passwordCheck.get());
    }

    public boolean valid(Runnable onNameEmpty, Runnable onNameNotEmpty, Runnable onPhoneEmpty, Runnable onPhoneNotEmpty) {
        boolean ret = true;

        if (name.get() != null && name.get().length() != 0) {
            onNameNotEmpty.run();
        } else {
            ret = false;
            onNameEmpty.run();
        }

        if (phone.get() != null && phone.get().length() != 0) {
            onPhoneNotEmpty.run();
        } else {
            ret = false;
            onPhoneEmpty.run();
        }

        return ret;
    }

    public void setData(ProfileResponseDto dto) {
        this.name.set(dto.getName());
        this.email.set(dto.getEmail());
        this.phone.set(dto.getPhone());
    }
}
