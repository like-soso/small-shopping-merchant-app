package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.coupon.dto.request.CouponModifyRequestDto;
import com.sososhopping.merchant.model.coupon.dto.response.CouponCheckResponseDto;
import com.sososhopping.merchant.model.coupon.entity.Coupon;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;

import java.util.function.Consumer;

public class CouponModifyViewModel extends ViewModel {

    private final ObservableField<String> userPhone = new ObservableField<>("");
    private final ObservableField<String> couponCode = new ObservableField<>("");

    ObservableField<String> couponName = new ObservableField<>("");
    ObservableField<String> couponAmount = new ObservableField<>("");
    ObservableField<String> couponMinPrice = new ObservableField<>("");
    ObservableField<String> couponExpiry = new ObservableField<>("");

    public ObservableField<String> getUserPhone() {
        return userPhone;
    }

    public ObservableField<String> getCouponCode() {
        return couponCode;
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

    public void requestCouponCheck(int storeId, Consumer<CouponCheckResponseDto> onSuccess) {
        CouponRepository.getInstance().requestCouponCheck(storeId, userPhone.get(), couponCode.get(), onSuccess);
    }

    public void requestCouponModify(int storeId, Runnable onSuccess) {
        CouponRepository.getInstance().requestCouponModify(storeId, this.toDto(), onSuccess);
    }

    private CouponModifyRequestDto toDto() {
        return new CouponModifyRequestDto(this.userPhone.get(), this.couponCode.get());
    }

    public void setModel(Coupon coupon) {
        this.couponName.set(coupon.getCouponName());
        this.couponAmount.set(coupon.getCouponType().equals("FIX") ? Integer.toString(coupon.getFixAmount()) : Double.toString(coupon.getRateAmount()));
        this.couponMinPrice.set(Integer.toString(coupon.getMinimumOrderPrice()));
        this.couponExpiry.set(coupon.getExpiryDate());
    }
}
