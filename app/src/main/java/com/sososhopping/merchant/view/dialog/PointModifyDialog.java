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
import com.sososhopping.merchant.databinding.DialogPointModifyBinding;
import com.sososhopping.merchant.viewmodel.PointModifyViewModel;

public class PointModifyDialog extends DialogFragment {
    private static final String STOREID = "storeId";
    private static final String USERNAME = "userName";
    private static final String USERPOINT = "userPoint";

    private int storeId;
    private String userName;
    private int userPoint;

    DialogPointModifyBinding binding;

    public PointModifyDialog() {

    }

    public PointModifyDialog newInstance() {
        PointModifyDialog dialog = new PointModifyDialog();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putString(USERNAME, userName);
        args.putInt(USERPOINT, userPoint);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            userName = getArguments().getString(USERNAME);
            userPoint = getArguments().getInt(USERPOINT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_point_modify, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireParentFragment());

        PointModifyViewModel viewModel = viewModelProvider.get(PointModifyViewModel.class);
        binding.setPointModifyViewModel(viewModel);

        binding.usernameValue.setText(userName);
        binding.currentPointValue.setText(Integer.toString(userPoint));

        binding.loginLogin.setEnabled(viewModel.getIsSave().get() || userPoint >= Integer.parseInt(viewModel.getAmount().get()));

        Runnable onSuccess = this::closeDialog;
        Runnable onError = this::onNetworkError;

        binding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestPointModify(storeId, onSuccess, onError);
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

    private void closeDialog() {
        this.dismiss();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
