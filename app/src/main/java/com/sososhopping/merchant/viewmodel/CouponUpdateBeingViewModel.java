package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.coupon.dto.request.CouponRegisterRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateBeingRequestDto;
import com.sososhopping.merchant.model.coupon.entity.Coupon;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;

public class CouponUpdateBeingViewModel extends ViewModel {
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

    public void requestRegister(int storeId, int couponId, Runnable onSuccess) {
        CouponRepository.getInstance().requestCouponUpdateBeing(storeId, couponId, this.toDto(), onSuccess);
    }

    private CouponUpdateBeingRequestDto toDto() {
        return new CouponUpdateBeingRequestDto(
                couponIssuedEnd.get()
        );
    }

    public void fromModel(Coupon coupon) {
        this.couponType.set(coupon.getCouponType());
        this.couponAmount.set(coupon.getCouponType().equals("FIX") ? Integer.toString(coupon.getFixAmount()) : Double.toString(coupon.getRateAmount()));
        this.couponName.set(coupon.getCouponName());
        this.couponExpiry.set(coupon.getExpiryDate());
        this.couponIssuedStart.set(coupon.getIssuedStartDate());
        this.couponIssuedEnd.set(coupon.getIssuedDueDate());
        this.couponMinPrice.set(Integer.toString(coupon.getMinimumOrderPrice()));
        this.couponQuantity.set(Integer.toString(coupon.getStockQuantity()));
    }
}
