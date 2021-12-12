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
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.DialogCouponModifyBinding;
import com.sososhopping.merchant.viewmodel.CouponModifyViewModel;

public class CouponModifyDialog extends DialogFragment {

    private static final String STOREID = "storeId";
    private static final String USERNAME = "userName";

    private int storeId;
    private String userName;

    DialogCouponModifyBinding binding;

    public CouponModifyDialog() {

    }

    public CouponModifyDialog newInstance() {
        CouponModifyDialog dialog = new CouponModifyDialog();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putString(USERNAME, userName);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            userName = getArguments().getString(USERNAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_modify, container, false);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireParentFragment());

        CouponModifyViewModel viewModel = viewModelProvider.get(CouponModifyViewModel.class);

        binding.setCouponModifyViewModel(viewModel);

        binding.usernameValue.setText(userName);

        binding.type.setText("FIX".equals(viewModel.getCouponType()) ? "원" : "%");

        Runnable onSuccess = this::onSuccess;
        Runnable onInvalid = this::onInvalid;
        Runnable onError = this::onError;

        binding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestCouponModify(storeId, onSuccess, onInvalid, onError);
            }
        });

        return binding.getRoot();
    }

    private void onError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }

    private void onInvalid() {
        NavHostFragment.findNavController(this).navigate(R.id.action_couponModifyDialog_to_invalidCouponDialog);
    }

    public void onSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_couponModifyDialog_to_couponUsedDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
