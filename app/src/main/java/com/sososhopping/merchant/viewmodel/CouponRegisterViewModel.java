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

    public boolean valid(Runnable onNameEmpty, Runnable onNameNotEmpty, Runnable onAmountEmpty, Runnable onAmountNotEmpty, Runnable onAmountInvalid, Runnable onMinPriceEmpty, Runnable onMinPriceNotEmpty, Runnable onMinPriceInvalid, Runnable onQuantityEmpty, Runnable onQuantityNotEmpty, Runnable onQuantityInvalid, Runnable onExpiryEmpty, Runnable onExpiryNotEmpty, Runnable onIssuedStartEmpty, Runnable onIssuedStartNotEmpty, Runnable onIssuedEndEmpty, Runnable onIssuedEndNotEmpty) {
        boolean ret = true;

        if(couponName.get() == null || couponName.get().isEmpty()) {
            ret = false;
            onNameEmpty.run();
        } else onNameNotEmpty.run();

        try {
            if(couponAmount.get() == null || couponAmount.get().isEmpty()) {
                ret = false;
                onAmountEmpty.run();
            } else if ("FIX".equals(couponType.get()) ? Integer.parseInt(couponAmount.get()) <= 0 : Double.parseDouble(couponAmount.get()) <= 0) {
                ret = false;
                onAmountInvalid.run();
            } else onAmountNotEmpty.run();
        } catch (Exception e) {
            ret = false;
            onAmountInvalid.run();
        }

        try {
            if(couponMinPrice.get() == null || couponMinPrice.get().isEmpty()) {
                ret = false;
                onMinPriceEmpty.run();
            } else if (Integer.parseInt(couponMinPrice.get()) <= 0) {
                ret = false;
                onMinPriceInvalid.run();
            } else onMinPriceNotEmpty.run();
        } catch (Exception e) {
            ret = false;
            onMinPriceInvalid.run();
        }

        try {
            if(couponQuantity.get() == null || couponQuantity.get().isEmpty()) {
                ret = false;
                onQuantityEmpty.run();
            } else if (Integer.parseInt(couponQuantity.get()) <= 0) {
                ret = false;
                onQuantityInvalid.run();
            } else onQuantityNotEmpty.run();
        } catch (Exception e) {
            ret = false;
            onQuantityInvalid.run();
        }

        if(couponIssuedStart.get() == null || couponIssuedStart.get().isEmpty()) {
            ret = false;
            onIssuedStartEmpty.run();
        } else onIssuedStartNotEmpty.run();

        if(couponIssuedEnd.get() == null || couponIssuedEnd.get().isEmpty()) {
            ret = false;
            onIssuedEndEmpty.run();
        } else onIssuedEndNotEmpty.run();

        if(couponExpiry.get() == null || couponExpiry.get().isEmpty()) {
            ret = false;
            onExpiryEmpty.run();
        } else onExpiryNotEmpty.run();

        return ret;
    }
}
