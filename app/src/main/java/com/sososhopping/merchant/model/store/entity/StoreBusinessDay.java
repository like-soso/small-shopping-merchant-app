package com.sososhopping.merchant.model.store.entity;

public class StoreBusinessDay {

    int id;
    String day;
    boolean isOpen;
    String openTime;
    String closeTime;

    public StoreBusinessDay(int id, String day, boolean isOpen, String openTime, String closeTime) {
        this.id = id;
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
