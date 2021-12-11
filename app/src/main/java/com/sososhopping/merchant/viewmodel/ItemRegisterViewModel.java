package com.sososhopping.merchant.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.item.dto.request.ItemRegisterRequestDto;
import com.sososhopping.merchant.model.item.repository.ItemRepository;

public class ItemRegisterViewModel extends ViewModel {

    ObservableField<Bitmap> bitmap = new ObservableField<>();

    ObservableField<String> name = new ObservableField<>();
    ObservableField<String> description = new ObservableField<>();
    ObservableField<String> unit = new ObservableField<>();
    ObservableField<String> unitPrice = new ObservableField<>();

    ObservableField<Boolean> salesStatus = new ObservableField<>(true);

    public ObservableField<Bitmap> getBitmap() {
        return bitmap;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getUnit() {
        return unit;
    }

    public ObservableField<String> getUnitPrice() {
        return unitPrice;
    }

    public ObservableField<Boolean> getSalesStatus() {
        return salesStatus;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.set(bitmap);
    }

    public void requestRegister(int storeId, Runnable onSuccess, Runnable onError) {
        System.out.println(this.name.get());
        ItemRepository.getInstance().requestRegisterItem(storeId, this.toDto(), this.bitmap.get(), onSuccess, onError);
    }

    private ItemRegisterRequestDto toDto() {
        System.out.println(unitPrice.get());
        return new ItemRegisterRequestDto(name.get(), description.get(), unit.get(), Integer.valueOf(unitPrice.get()), salesStatus.get());
    }
}
