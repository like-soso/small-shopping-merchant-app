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

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireParentFragment());

        viewModel = viewModelProvider.get(CouponModifyViewModel.class);
        binding.setCouponModifyViewModel(viewModel);

        Consumer<CouponCheckResponseDto> onSuccess = this::onSuccess;
        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestCouponCheck(storeId, onSuccess);
            }
        });

        return binding.getRoot();
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
