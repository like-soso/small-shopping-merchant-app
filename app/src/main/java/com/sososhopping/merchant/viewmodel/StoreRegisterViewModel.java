package com.sososhopping.merchant.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.store.dto.request.StoreBusinessDaysDto;
import com.sososhopping.merchant.model.store.dto.request.StoreMetadataDto;
import com.sososhopping.merchant.model.store.dto.request.StoreRegisterRequestDto;
import com.sososhopping.merchant.model.store.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

public class StoreRegisterViewModel extends ViewModel {
    ObservableField<Bitmap> bitmap = new ObservableField<>();

    ObservableField<String> name = new ObservableField<>("");
    ObservableField<String> description = new ObservableField<>("");
    ObservableField<String> phone = new ObservableField<>("");

    ObservableField<String> category = new ObservableField<>("");

    boolean openMonday = false;
    ObservableField<String> openHourMonday = new ObservableField<>("");
    ObservableField<String> closeHourMonday = new ObservableField<>("");

    boolean openTuesday = false;
    ObservableField<String> openHourTuesday = new ObservableField<>("");
    ObservableField<String> closeHourTuesday = new ObservableField<>("");

    boolean openWednesday = false;
    ObservableField<String> openHourWednesday = new ObservableField<>("");
    ObservableField<String> closeHourWednesday = new ObservableField<>("");

    boolean openThursday = false;
    ObservableField<String> openHourThursday = new ObservableField<>("");
    ObservableField<String> closeHourThursday = new ObservableField<>("");

    boolean openFriday = false;
    ObservableField<String> openHourFriday = new ObservableField<>("");
    ObservableField<String> closeHourFriday = new ObservableField<>("");

    boolean openSaturday = false;
    ObservableField<String> openHourSaturday = new ObservableField<>("");
    ObservableField<String> closeHourSaturday = new ObservableField<>("");

    boolean openSunday = false;
    ObservableField<String> openHourSunday = new ObservableField<>("");
    ObservableField<String> closeHourSunday = new ObservableField<>("");

    ObservableField<String> openHourDetail = new ObservableField<>("");

    boolean delivery = false;
    ObservableField<Boolean> localCurrency = new ObservableField<>(false);

    ObservableField<String> businessNumber = new ObservableField<>("");
    ObservableField<String> repName = new ObservableField<>("");
    ObservableField<String> businessName = new ObservableField<>("");
    ObservableField<String> businessOpenDate = new ObservableField<>("");

    ObservableField<String> streetAddress = new ObservableField<>("");
    ObservableField<String> detailedAddress = new ObservableField<>("");

    ObservableField<String> lat = new ObservableField<>("");
    ObservableField<String> lng = new ObservableField<>("");

    ObservableField<String> deliveryCharge = new ObservableField<>("");

    public ObservableField<String> getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat.set(lat);
    }

    public ObservableField<String> getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng.set(lng);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.set(bitmap);
    }

    public ObservableField<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public ObservableField<Bitmap> getBitmap() {
        return bitmap;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public ObservableField<String> getOpenHourMonday() {
        return openHourMonday;
    }

    public ObservableField<String> getCloseHourMonday() {
        return closeHourMonday;
    }

    public ObservableField<String> getOpenHourTuesday() {
        return openHourTuesday;
    }

    public ObservableField<String> getCloseHourTuesday() {
        return closeHourTuesday;
    }

    public ObservableField<String> getOpenHourWednesday() {
        return openHourWednesday;
    }

    public ObservableField<String> getCloseHourWednesday() {
        return closeHourWednesday;
    }

    public ObservableField<String> getOpenHourThursday() {
        return openHourThursday;
    }

    public ObservableField<String> getCloseHourThursday() {
        return closeHourThursday;
    }

    public ObservableField<String> getOpenHourFriday() {
        return openHourFriday;
    }

    public ObservableField<String> getCloseHourFriday() {
        return closeHourFriday;
    }

    public ObservableField<String> getOpenHourSaturday() {
        return openHourSaturday;
    }

    public ObservableField<String> getCloseHourSaturday() {
        return closeHourSaturday;
    }

    public ObservableField<String> getOpenHourSunday() {
        return openHourSunday;
    }

    public ObservableField<String> getCloseHourSunday() {
        return closeHourSunday;
    }

    public ObservableField<String> getOpenHourDetail() {
        return openHourDetail;
    }

    public void setOpenMonday(boolean openMonday) {
        this.openMonday = openMonday;
    }

    public void setOpenTuesday(boolean openTuesday) {
        this.openTuesday = openTuesday;
    }

    public void setOpenWednesday(boolean openWednesday) {
        this.openWednesday = openWednesday;
    }

    public void setOpenThursday(boolean openThursday) {
        this.openThursday = openThursday;
    }

    public void setOpenFriday(boolean openFriday) {
        this.openFriday = openFriday;
    }

    public void setOpenSaturday(boolean openSaturday) {
        this.openSaturday = openSaturday;
    }

    public void setOpenSunday(boolean openSunday) {
        this.openSunday = openSunday;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public ObservableField<Boolean> getLocalCurrency() {
        return localCurrency;
    }

    public ObservableField<String> getBusinessNumber() {
        return businessNumber;
    }

    public ObservableField<String> getRepName() {
        return repName;
    }

    public ObservableField<String> getBusinessName() {
        return businessName;
    }

    public ObservableField<String> getBusinessOpenDate() {
        return businessOpenDate;
    }

    public ObservableField<String> getStreetAddress() {
        return streetAddress;
    }

    public ObservableField<String> getDetailedAddress() {
        return detailedAddress;
    }

    public ObservableField<String> getDeliveryCharge() {
        return deliveryCharge;
    }

    public void requestRegister(Runnable onSuccess, Runnable onInvalid, Runnable onError) {
        StoreRepository.getInstance().requestRegister(bitmap.get(), toDto(), onSuccess, onInvalid, onError);
    }


    private StoreRegisterRequestDto toDto() {
        return new StoreRegisterRequestDto(
                name.get(),
                description.get(),
                phone.get(),
                category.get(),
                openHourDetail.get(),
                localCurrency.get(),
                delivery,
                streetAddress.get(),
                detailedAddress.get(),
                lat.get(),
                lng.get(),
                buildStoreBusinessDaysList(),
                buildStoreMetadataDto(),
                delivery ? Integer.parseInt(deliveryCharge.get()) : 0
        );
    }

    private List<StoreBusinessDaysDto> buildStoreBusinessDaysList() {
        List<StoreBusinessDaysDto> daysDtos = new ArrayList<>();

        daysDtos.add(new StoreBusinessDaysDto("월", openMonday, openMonday ? openHourMonday.get() : null, openMonday ? closeHourMonday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("화", openTuesday, openTuesday ? openHourTuesday.get() : null, openTuesday ? closeHourTuesday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("수", openWednesday, openWednesday ? openHourWednesday.get() : null, openWednesday ? closeHourWednesday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("목", openThursday, openThursday ? openHourThursday.get() : null, openThursday ? closeHourThursday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("금", openFriday, openFriday ? openHourFriday.get() : null, openFriday ? closeHourFriday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("토", openSaturday, openSaturday ? openHourSaturday.get() : null, openSaturday ? closeHourSaturday.get() : null));
        daysDtos.add(new StoreBusinessDaysDto("일", openSunday, openSunday ? openHourSunday.get() : null, openSunday ? closeHourSunday.get() : null));

        return daysDtos;
    }

    private StoreMetadataDto buildStoreMetadataDto() {
        return new StoreMetadataDto(
                businessNumber.get(),
                repName.get(),
                businessName.get(),
                businessOpenDate.get()
        );
    }

    public boolean validateBasicForm(Runnable onNameEmpty, Runnable onNameNotEmpty, Runnable onPhoneEmpty, Runnable onPhoneNotEmpty, Runnable onCategoryEmpty, Runnable onCategoryNotEmpty, Runnable onMondayConsistent, Runnable onMondayInconsistent, Runnable onTuesdayConsistent, Runnable onTuesdayInconsistent, Runnable onWednesdayConsistent, Runnable onWednesdayInconsistent, Runnable onThursdayConsistent, Runnable onThursdayInconsistent, Runnable onFridayConsistent, Runnable onFridayInconsistent, Runnable onSaturdayConsistent, Runnable onSaturdayInconsistent, Runnable onSundayConsistent, Runnable onSundayInconsistent, Runnable onDeliveryConsistent, Runnable onDeliveryInconsistent) {
        boolean ret = true;

        if (name.get() == null || name.get().isEmpty()) {
            ret = false;
            onNameEmpty.run();
        } else onNameNotEmpty.run();

        if (phone.get() == null || phone.get().isEmpty()) {
            ret = false;
            onPhoneEmpty.run();
        } else onPhoneNotEmpty.run();

        if (category.get() == null || category.get().isEmpty() || "카테고리".equals(category.get())) {
            ret = false;
            onCategoryEmpty.run();
        } else onCategoryNotEmpty.run();

        if (openMonday && (openHourMonday.get() == null || openHourMonday.get().isEmpty() || closeHourMonday.get() == null || closeHourMonday.get().isEmpty())) {
            ret = false;
            onMondayInconsistent.run();
        } else onMondayConsistent.run();

        if (openTuesday && (openHourTuesday.get() == null || openHourTuesday.get().isEmpty() || closeHourTuesday.get() == null || closeHourTuesday.get().isEmpty())) {
            ret = false;
            onTuesdayInconsistent.run();
        } else onTuesdayConsistent.run();

        if (openWednesday && (openHourWednesday.get() == null || openHourWednesday.get().isEmpty() || closeHourWednesday.get() == null || closeHourWednesday.get().isEmpty())) {
            ret = false;
            onWednesdayInconsistent.run();
        } else onWednesdayConsistent.run();

        if (openThursday && (openHourThursday.get() == null || openHourThursday.get().isEmpty() || closeHourThursday.get() == null || closeHourThursday.get().isEmpty())) {
            ret = false;
            onThursdayInconsistent.run();
        } else onThursdayConsistent.run();

        if (openFriday && (openHourFriday.get() == null || openHourFriday.get().isEmpty() || closeHourFriday.get() == null || closeHourFriday.get().isEmpty())) {
            ret = false;
            onFridayInconsistent.run();
        } else onFridayConsistent.run();

        if (openSaturday && (openHourSaturday.get() == null || openHourSaturday.get().isEmpty() || closeHourSaturday.get() == null || closeHourSaturday.get().isEmpty())) {
            ret = false;
            onSaturdayInconsistent.run();
        } else onSaturdayConsistent.run();

        if (openSunday && (openHourSunday.get() == null || openHourSunday.get().isEmpty() || closeHourSunday.get() == null || closeHourSunday.get().isEmpty())) {
            ret = false;
            onSundayInconsistent.run();
        } else onSundayConsistent.run();

        if (delivery && (deliveryCharge.get() == null || deliveryCharge.get().isEmpty())) {
            ret = false;
            onDeliveryInconsistent.run();
        } else onDeliveryConsistent.run();

        return ret;
    }

    public boolean validateMetaForm(Runnable onBusinessNumEmpty, Runnable onBusinessNumNotEmpty, Runnable onOwnerNameEmpty, Runnable onOwnerNameNotEmpty, Runnable onBusinessNameEmpty, Runnable onBusinessNameNotEmpty, Runnable onOpenDateEmpty, Runnable onOpenDateNotEmpty, Runnable onAddressNotChecked) {
        boolean ret = true;

        if (businessNumber.get() == null || businessNumber.get().isEmpty()) {
            ret = false;
            onBusinessNumEmpty.run();
        } else onBusinessNumNotEmpty.run();

        if (repName.get() == null || repName.get().isEmpty()) {
            ret = false;
            onOwnerNameEmpty.run();
        } else onOwnerNameNotEmpty.run();

        if (businessName.get() == null || businessName.get().isEmpty()) {
            ret = false;
            onBusinessNameEmpty.run();
        } else onBusinessNameNotEmpty.run();

        if (businessOpenDate.get() == null || businessOpenDate.get().isEmpty()) {
            ret = false;
            onOpenDateEmpty.run();
        } else onOpenDateNotEmpty.run();

        if (lat.get() == null || lat.get().isEmpty() || lng.get() == null || lng.get().isEmpty()) {
            ret = false;
            onAddressNotChecked.run();
        }

        return ret;
    }
}
