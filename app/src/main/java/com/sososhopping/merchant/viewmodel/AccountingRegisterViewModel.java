package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.accounting.dto.request.AccountingRegisterAndUpdateRequestDto;
import com.sososhopping.merchant.model.accounting.repository.AccountingRepository;

public class AccountingRegisterViewModel extends ViewModel {

    private final ObservableField<String> description = new ObservableField<>();
    private final ObservableField<String> date = new ObservableField<>();
    private final ObservableField<String> time = new ObservableField<>();
    private final ObservableField<String> amount = new ObservableField<>();

    private boolean isIncome = true;

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getAmount() {
        return amount;
    }

    public void setIsIncome(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public void requestRegister(int storeId, Runnable onSuccess, Runnable onError) {
        AccountingRepository.getInstance().requestAccountingRegister(storeId, toDto(), onSuccess, onError);
    }

    private AccountingRegisterAndUpdateRequestDto toDto() {
        return new AccountingRegisterAndUpdateRequestDto(date.get() + " " + time.get(),
                isIncome ? Integer.parseInt(amount.get()) : Integer.parseInt(amount.get()) * -1,
                description.get());
    }

    public boolean validate(Runnable onMemoEmpty, Runnable onMemoNotEmpty, Runnable onAmountEmpty, Runnable onAmountNegative, Runnable onAmountNotEmpty, Runnable onDateEmpty, Runnable onDateNotEmpty, Runnable onTimeEmpty, Runnable onTimeNotEmpty) {
        boolean ret = true;

        if (description.get() == null || description.get().isEmpty()) {
            onMemoEmpty.run();
            ret = false;
        } else {
            onMemoNotEmpty.run();
        }

        if (amount.get() == null || amount.get().isEmpty()) {
            onAmountEmpty.run();
            ret = false;
        } else if (Integer.parseInt(amount.get()) <= 0){
            onAmountNegative.run();
            ret = false;
        } else {
            onAmountNotEmpty.run();
        }

        if (date.get() == null || date.get().isEmpty()) {
            onDateEmpty.run();
            ret = false;
        } else {
            onDateNotEmpty.run();
        }

        if (time.get() == null || time.get().isEmpty()) {
            onTimeEmpty.run();
            ret = false;
        } else {
            onTimeNotEmpty.run();
        }

        return ret;
    }
}
