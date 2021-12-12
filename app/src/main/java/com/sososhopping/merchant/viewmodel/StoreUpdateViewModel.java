package com.sososhopping.merchant.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.store.dto.request.StoreBusinessDaysDto;
import com.sososhopping.merchant.model.store.dto.request.StoreUpdateRequestDto;
import com.sososhopping.merchant.model.store.entity.StoreBusinessDay;
import com.sososhopping.merchant.model.store.entity.StoreDetail;
import com.sososhopping.merchant.model.store.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

public class StoreUpdateViewModel extends ViewModel {
    ObservableField<Bitmap> bitmap = new ObservableField<>();

    ObservableField<String> name = new ObservableField<>();
    ObservableField<String> description = new ObservableField<>();
    ObservableField<String> phone = new ObservableField<>();

    ObservableField<String> category = new ObservableField<>();

    int mondayId;
    boolean openMonday = false;
    ObservableField<String> openHourMonday = new ObservableField<>();
    ObservableField<String> closeHourMonday = new ObservableField<>();

    int tuesdayId;
    boolean openTuesday = false;
    ObservableField<String> openHourTuesday = new ObservableField<>();
    ObservableField<String> closeHourTuesday = new ObservableField<>();

    int wednesdayId;
    boolean openWednesday = false;
    ObservableField<String> openHourWednesday = new ObservableField<>();
    ObservableField<String> closeHourWednesday = new ObservableField<>();

    int thursdayId;
    boolean openThursday = false;
    ObservableField<String> openHourThursday = new ObservableField<>();
    ObservableField<String> closeHourThursday = new ObservableField<>();

    int fridayId;
    boolean openFriday = false;
    ObservableField<String> openHourFriday = new ObservableField<>();
    ObservableField<String> closeHourFriday = new ObservableField<>();

    int saturdayId;
    boolean openSaturday = false;
    ObservableField<String> openHourSaturday = new ObservableField<>();
    ObservableField<String> closeHourSaturday = new ObservableField<>();

    int sundayId;
    boolean openSunday = false;
    ObservableField<String> openHourSunday = new ObservableField<>();
    ObservableField<String> closeHourSunday = new ObservableField<>();

    ObservableField<String> openHourDetail = new ObservableField<>();

    boolean delivery = false;
    ObservableField<Boolean> localCurrency = new ObservableField<>(false);

    ObservableField<String> deliveryCharge = new ObservableField<>();

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

    public ObservableField<String> getCategory() {
        return category;
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

    public ObservableField<Boolean> getLocalCurrency() {
        return localCurrency;
    }

    public ObservableField<String> getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.set(bitmap);
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public void requestUpdate(int storeId, Runnable onSuccess) {
        StoreRepository.getInstance().requestStoreUpdate(storeId, this.bitmap.get(), toDto(), onSuccess);
    }

    public void setItem(StoreDetail storeDetail) {
        this.name.set(storeDetail.getName());
        this.description.set(storeDetail.getDescription());
        this.deliveryCharge.set(Integer.toString(storeDetail.getDeliveryCharge()));
        this.localCurrency.set(storeDetail.getLocalCurrencyStatus());
        this.phone.set(storeDetail.getPhone());
        this.openHourDetail.set(storeDetail.getExtraBusinessDay());
        this.delivery = storeDetail.getDeliveryStatus();
        this.category.set(storeDetail.getStoreType());
        for (StoreBusinessDay day : storeDetail.getStoreBusinessDays()) {
            switch (day.getDay()) {
                case "월":{
                    mondayId = day.getId();
                    openMonday = day.isOpen();
                    openHourMonday.set(day.getOpenTime());
                    closeHourMonday.set(day.getCloseTime());
                    break;
                }
                case "화":{
                    tuesdayId = day.getId();
                    openTuesday = day.isOpen();
                    openHourTuesday.set(day.getOpenTime());
                    closeHourTuesday.set(day.getCloseTime());
                    break;
                }
                case "수":{
                    wednesdayId = day.getId();
                    openWednesday = day.isOpen();
                    openHourWednesday.set(day.getOpenTime());
                    closeHourWednesday.set(day.getCloseTime());
                    break;
                }
                case "목":{
                    thursdayId = day.getId();
                    openThursday = day.isOpen();
                    openHourThursday.set(day.getOpenTime());
                    closeHourThursday.set(day.getCloseTime());
                    break;
                }
                case "금":{
                    fridayId = day.getId();
                    openFriday = day.isOpen();
                    openHourFriday.set(day.getOpenTime());
                    closeHourFriday.set(day.getCloseTime());
                    break;
                }
                case "토":{
                    saturdayId = day.getId();
                    openSaturday = day.isOpen();
                    openHourSaturday.set(day.getOpenTime());
                    closeHourSaturday.set(day.getCloseTime());
                    break;
                }
                case "일":{
                    sundayId = day.getId();
                    openSunday = day.isOpen();
                    openHourSunday.set(day.getOpenTime());
                    closeHourSunday.set(day.getCloseTime());
                    break;
                }
            }
        }
    }

    private StoreUpdateRequestDto toDto() {
        return new StoreUpdateRequestDto(
                this.name.get(),
                this.description.get(),
                this.phone.get(),
                this.category.get(),
                this.openHourDetail.get(),
                this.localCurrency.get(),
                this.delivery,
                buildStoreBusinessDaysList(),
                Integer.parseInt(this.deliveryCharge.get())
        );
    }

    private List<StoreBusinessDay> buildStoreBusinessDaysList() {
        List<StoreBusinessDay> days = new ArrayList<>();

        days.add(new StoreBusinessDay(mondayId, "월", openMonday, openMonday ? openHourMonday.get() : null, openMonday ? closeHourMonday.get() : null));
        days.add(new StoreBusinessDay(tuesdayId, "화", openTuesday, openTuesday ? openHourTuesday.get() : null, openTuesday ? closeHourTuesday.get() : null));
        days.add(new StoreBusinessDay(wednesdayId, "수", openWednesday, openWednesday ? openHourWednesday.get() : null, openWednesday ? closeHourWednesday.get() : null));
        days.add(new StoreBusinessDay(thursdayId, "목", openThursday, openThursday ? openHourThursday.get() : null, openThursday ? closeHourThursday.get() : null));
        days.add(new StoreBusinessDay(fridayId, "금", openFriday, openFriday ? openHourFriday.get() : null, openFriday ? closeHourFriday.get() : null));
        days.add(new StoreBusinessDay(saturdayId, "토", openSaturday, openSaturday ? openHourSaturday.get() : null, openSaturday ? closeHourSaturday.get() : null));
        days.add(new StoreBusinessDay(sundayId, "일", openSunday, openSunday ? openHourSunday.get() : null, openSunday ? closeHourSunday.get() : null));

        return days;
    }

    public boolean validate(Runnable onNameEmpty, Runnable onNameNotEmpty, Runnable onPhoneEmpty, Runnable onPhoneNotEmpty, Runnable onCategoryEmpty, Runnable onCategoryNotEmpty, Runnable onMondayConsistent, Runnable onMondayInconsistent, Runnable onTuesdayConsistent, Runnable onTuesdayInconsistent, Runnable onWednesdayConsistent, Runnable onWednesdayInconsistent, Runnable onThursdayConsistent, Runnable onThursdayInconsistent, Runnable onFridayConsistent, Runnable onFridayInconsistent, Runnable onSaturdayConsistent, Runnable onSaturdayInconsistent, Runnable onSundayConsistent, Runnable onSundayInconsistent, Runnable onDeliveryConsistent, Runnable onDeliveryInconsistent) {
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
}
