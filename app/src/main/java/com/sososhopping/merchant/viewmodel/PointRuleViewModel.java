package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.point.dto.request.PointRuleRequestDto;
import com.sososhopping.merchant.model.point.repository.PointRepository;

public class PointRuleViewModel extends ViewModel {

    boolean usePoint = false;
    ObservableField<String> pointRate = new ObservableField<>();

    public boolean getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(boolean usePoint) {
        this.usePoint = usePoint;
    }

    public ObservableField<String> getPointRate() {
        return pointRate;
    }

    public void requestUpdate(int storeId, Runnable onSuccess, Runnable onError){
        PointRepository.getInstance().requestPointRuleUpdate(storeId, this.toDto(), onSuccess, onError);
    }

    public boolean validate(Runnable onValid, Runnable onInvalid) {
        try {
            if (!usePoint || (pointRate.get() != null && ((Double.parseDouble(pointRate.get()) > 0) || (Double.parseDouble(pointRate.get()) <= 100)))) {
                onValid.run();
                return true;
            }
            else {
                onInvalid.run();
                return false;
            }
        } catch (Exception e){
            onInvalid.run();
            return false;
        }
    }

    private PointRuleRequestDto toDto() {
        return new PointRuleRequestDto(usePoint, usePoint ? Double.valueOf(pointRate.get()) : 0);
    }
}
