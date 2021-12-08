package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

public class AccountingListViewModel extends ViewModel {
    ObservableField<String> year = new ObservableField<>(Integer.toString(LocalDate.now().getYear()));
    ObservableField<String> month = new ObservableField<>(Integer.toString(LocalDate.now().getMonthValue()));

    public ObservableField<String> getYear() {
        return year;
    }

    public ObservableField<String> getMonth() {
        return month;
    }

    public String getDateString() {
        if (Integer.parseInt(month.get()) < 10) return year.get() + "/" + "0" + month.get();
        return year.get() + "/" + month.get();
    }

    public void toPrevMonth() {
        if (month.get().equals("1")) {
            month.set("12");
            year.set(Integer.toString(Integer.parseInt(year.get()) - 1));
        } else {
            month.set(Integer.toString(Integer.parseInt(month.get()) - 1));
        }
    }

    public void toNextMonth() {
        if (month.get().equals("12")) {
            month.set("1");
            year.set(Integer.toString(Integer.parseInt(year.get()) + 1));
        } else {
            month.set(Integer.toString(Integer.parseInt(month.get()) + 1));
        }
    }
}
