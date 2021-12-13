package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.accounting.dto.request.AccountingRegisterAndUpdateRequestDto;
import com.sososhopping.merchant.model.accounting.entity.Accounting;
import com.sososhopping.merchant.model.accounting.repository.AccountingRepository;

public class AccountingUpdateViewModel extends ViewModel {

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

    public void requestUpdate(int storeId, int accountingId, Runnable onSuccess, Runnable onError) {
        AccountingRepository.getInstance().requestAccountingUpdate(storeId, accountingId, toDto(), onSuccess, onError);
    }

    private AccountingRegisterAndUpdateRequestDto toDto() {
        return new AccountingRegisterAndUpdateRequestDto(date.get() + " " + time.get(),
                isIncome ? Integer.parseInt(amount.get()) : Integer.parseInt(amount.get()) * -1,
                description.get());
    }

    public boolean validate(Runnable onAmountEmpty, Runnable onAmountNegative, Runnable onAmountNotEmpty, Runnable onDateEmpty, Runnable onDateNotEmpty, Runnable onTimeEmpty, Runnable onTimeNotEmpty) {
        boolean ret = true;

        try {
            if (amount.get() == null || amount.get().isEmpty()) {
                onAmountEmpty.run();
                ret = false;
            } else if (Integer.parseInt(amount.get()) <= 0){
                onAmountNegative.run();
                ret = false;
            } else {
                onAmountNotEmpty.run();
            }
        } catch (Exception e) {
            ret = false;
            onAmountNegative.run();
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

    public void fromModel(Accounting accounting) {
        this.isIncome = accounting.getAmount() > 0;
        this.description.set(accounting.getMemo());
        this.amount.set(Integer.toString(accounting.getAmount() > 0 ? accounting.getAmount() : (accounting.getAmount() * -1)));
        this.date.set(accounting.getDateTime().split(" ")[0]);
        this.time.set(accounting.getDateTime().split(" ")[1]);
    }
}
