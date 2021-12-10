package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.coupon.dto.request.CouponModifyRequestDto;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;

public class CouponModifyViewModel extends ViewModel {

    private final ObservableField<String> userPhone = new ObservableField<>();
    private final ObservableField<String> couponCode = new ObservableField<>();

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public ObservableField<String> getCouponCode() {
        return couponCode;
    }

    public void requestCouponCheck(int storeId, Runnable onSuccess) {
        CouponRepository.getInstance().requestCouponCheck(storeId, userPhone.get(), couponCode.get(), onSuccess);
    }

    public void requestCouponModify(int storeId) {
        CouponRepository.getInstance().requestCouponModify(storeId, this.toDto());
    }

    private CouponModifyRequestDto toDto() {
        return new CouponModifyRequestDto(this.userPhone.get(), this.couponCode.get());
    }
}
