package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.EmailFindRequestDto;
import com.sososhopping.merchant.model.auth.dto.response.EmailFindResponseDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

import java.util.function.Consumer;

public class EmailFindViewModel extends ViewModel {

    private final ObservableField<String> name = new ObservableField<>("");
    private final ObservableField<String> phone = new ObservableField<>("");

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public void requestEmailFind(Consumer<EmailFindResponseDto> onSuccess, Runnable onInvalid, Runnable onError) {
        AuthRepository.getInstance().requestEmailFind(this.toDto(), onSuccess, onInvalid, onError);
    }

    private EmailFindRequestDto toDto() {
        return new EmailFindRequestDto(name.get(), phone.get());
    }
}
