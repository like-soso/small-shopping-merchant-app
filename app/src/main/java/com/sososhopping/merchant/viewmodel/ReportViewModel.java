package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.report.dto.request.UserReportRequestDto;
import com.sososhopping.merchant.model.report.repository.ReportRepository;

public class ReportViewModel extends ViewModel {

    private final ObservableField<String> content = new ObservableField<>();

    public ObservableField<String> getContent() {
        return content;
    }

    public void requestReport(int storeId, int userId, String userName, Runnable onSuccess) {
        ReportRepository.getInstance().requestReport(storeId, this.toDto(userId, userName), onSuccess);
    }

    private UserReportRequestDto toDto(int userId, String userName) {
        return new UserReportRequestDto(userId, userName, content.get());
    }
}
