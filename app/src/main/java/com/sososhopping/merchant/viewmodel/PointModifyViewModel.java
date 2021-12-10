package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.point.dto.request.PointModifyRequestDto;
import com.sososhopping.merchant.model.point.dto.response.PointCheckResponseDto;
import com.sososhopping.merchant.model.point.repository.PointRepository;

import java.util.function.Consumer;

public class PointModifyViewModel extends ViewModel {

    private final ObservableField<String> userPhone = new ObservableField<>();
    private final ObservableField<String> amount = new ObservableField<>();

    private final ObservableField<Boolean> isSave = new ObservableField<>(true);

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public ObservableField<String> getAmount() {
        return amount;
    }

    public ObservableField<Boolean> getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave.set(isSave);
    }

    public void requestUserCheck(int storeId, Consumer<PointCheckResponseDto> onSuccess, Runnable onFailed, Runnable onError) {
        PointRepository.getInstance().requestPointCheck(storeId, userPhone.get(), onSuccess, onFailed, onError);
    }

    public void requestPointModify(int storeId, Runnable onSuccess, Runnable onError) {
        PointRepository.getInstance().requestPointModify(storeId, this.toDto(), onSuccess, onError);
    }

    private PointModifyRequestDto toDto() {
        return new PointModifyRequestDto(userPhone.get(), Integer.parseInt(amount.get()), isSave.get());
    }
}
