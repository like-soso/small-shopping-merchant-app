package com.sososhopping.merchant.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.DialogPointCheckBinding;
import com.sososhopping.merchant.model.point.dto.response.PointCheckResponseDto;
import com.sososhopping.merchant.viewmodel.PointModifyViewModel;

import java.util.function.Consumer;

public class PointCheckDialog extends DialogFragment {
    private static final String STOREID = "storeId";

    private int storeId;

    DialogPointCheckBinding binding;

    public PointCheckDialog() {

    }

    public PointCheckDialog newInstance() {
        PointCheckDialog dialog = new PointCheckDialog();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_point_check, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireParentFragment());

        PointModifyViewModel viewModel = viewModelProvider.get(PointModifyViewModel.class);
        binding.setPointModifyViewModel(viewModel);

        Consumer<PointCheckResponseDto> onSuccess = this::onSuccess;
        Runnable onFailed = this::onUserNotFound;
        Runnable onError = this::onNetworkError;

        Runnable onValid = this::onPointValid;
        Runnable onInvalid = this::onPointInvalid;
        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.valid(onValid, onInvalid)){
                    viewModel.requestUserCheck(storeId, onSuccess, onFailed, onError);
                }
            }
        });

        binding.type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.earn) viewModel.setIsSave(true);
                else viewModel.setIsSave(false);
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

    public void onSuccess(PointCheckResponseDto dto) {
        Bundle bundle = new Bundle();
        bundle.putInt(STOREID, storeId);
        bundle.putString("userName", dto.getUserName());
        bundle.putInt("userPoint", dto.getAmount());
        NavHostFragment.findNavController(this).navigate(R.id.action_pointCheckDialog_to_pointModifyDialog, bundle);
    }

    public void onUserNotFound() {
        binding.customerNumber.setErrorEnabled(true);
        binding.customerNumber.setError("일치하는 고객을 찾을 수 없습니다.");
    }

    public void onPointValid() {
        binding.point.setErrorEnabled(true);
        binding.point.setError("유효하지 않은 입력입니다.");
    }

    public void onPointInvalid() {
        binding.point.setErrorEnabled(false);
        binding.point.setError(null);
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
