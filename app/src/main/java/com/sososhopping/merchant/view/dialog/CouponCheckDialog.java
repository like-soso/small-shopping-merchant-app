package com.sososhopping.merchant.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.DialogCouponCheckBinding;
import com.sososhopping.merchant.model.coupon.dto.response.CouponCheckResponseDto;
import com.sososhopping.merchant.viewmodel.CouponModifyViewModel;

import java.util.function.Consumer;

public class CouponCheckDialog extends DialogFragment {

    private static final String STOREID = "storeId";

    private int storeId;

    DialogCouponCheckBinding binding;
    CouponModifyViewModel viewModel;

    public CouponCheckDialog() {

    }

    public CouponCheckDialog newInstance() {
        CouponCheckDialog dialog = new CouponCheckDialog();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_check, container, false);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireParentFragment());

        viewModel = viewModelProvider.get(CouponModifyViewModel.class);
        binding.setCouponModifyViewModel(viewModel);

        Consumer<CouponCheckResponseDto> onSuccess = this::onSuccess;
        Runnable onFailed = this::onFailed;
        Runnable onInvalid = this::onInvalid;
        Runnable onError = this::onError;
        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestCouponCheck(storeId, onSuccess, onFailed, onInvalid, onError);
            }
        });

        return binding.getRoot();
    }

    private void onError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }

    private void onInvalid() {
        binding.customerNumber.setErrorEnabled(false);
        binding.customerNumber.setError(null);
        binding.point.setErrorEnabled(true);
        binding.point.setError("이 점포의 쿠폰 번호가 아닙니다.");
    }

    private void onFailed() {
        binding.customerNumber.setErrorEnabled(true);
        binding.customerNumber.setError("입력하신 정보가 올바르지 않습니다.");
        binding.point.setErrorEnabled(true);
        binding.point.setError("입력하신 정보가 올바르지 않습니다.");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void onSuccess(CouponCheckResponseDto dto) {
        Bundle bundle = new Bundle();
        bundle.putInt(STOREID, storeId);
        bundle.putString("userName", dto.getUserName());
        viewModel.setModel(dto.getCoupon());
        NavHostFragment.findNavController(this).navigate(R.id.action_couponCheckDialog_to_couponModifyDialog, bundle);
    }
}
