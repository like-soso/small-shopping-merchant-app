package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.coupon.dto.request.CouponRegisterRequestDto;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;

public class CouponRegisterViewModel extends ViewModel {
    ObservableField<String> couponType = new ObservableField<>("FIX");
    ObservableField<String> couponName = new ObservableField<>();
    ObservableField<String> couponAmount = new ObservableField<>();
    ObservableField<String> couponMinPrice = new ObservableField<>();
    ObservableField<String> couponExpiry = new ObservableField<>();
    ObservableField<String> couponQuantity = new ObservableField<>();
    ObservableField<String> couponIssuedStart = new ObservableField<>();
    ObservableField<String> couponIssuedEnd = new ObservableField<>();

    public ObservableField<String> getCouponType() {
        return couponType;
    }

    public ObservableField<String> getCouponName() {
        return couponName;
    }

    public ObservableField<String> getCouponAmount() {
        return couponAmount;
    }

    public ObservableField<String> getCouponMinPrice() {
        return couponMinPrice;
    }

    public ObservableField<String> getCouponExpiry() {
        return couponExpiry;
    }

    public ObservableField<String> getCouponQuantity() {
        return couponQuantity;
    }

    public ObservableField<String> getCouponIssuedStart() {
        return couponIssuedStart;
    }

    public ObservableField<String> getCouponIssuedEnd() {
        return couponIssuedEnd;
    }

    public void setCouponType(String couponType) {
        this.couponType.set(couponType);
    }

    public void requestRegister(int storeId, Runnable onSuccess) {
        CouponRepository.getInstance().requestRegister(storeId, this.toDto(), onSuccess);
    }

    private CouponRegisterRequestDto toDto() {
        return new CouponRegisterRequestDto(
                couponName.get(),
                Integer.parseInt(couponQuantity.get()),
                Integer.parseInt(couponMinPrice.get()),
                couponIssuedStart.get(),
                couponIssuedEnd.get(),
                couponExpiry.get(),
                couponType.get(),
                couponType.get().equals("RATE") ? Double.parseDouble(couponAmount.get()) : 0,
                couponType.get().equals("FIX") ? Integer.parseInt(couponAmount.get()) : 0
        );
    }
}
