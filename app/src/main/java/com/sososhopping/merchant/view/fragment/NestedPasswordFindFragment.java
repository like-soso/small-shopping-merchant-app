package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedPasswordFindBinding;
import com.sososhopping.merchant.viewmodel.PasswordFindViewModel;

public class NestedPasswordFindFragment extends Fragment {

    FragmentNestedPasswordFindBinding binding;

    public NestedPasswordFindFragment() {

    }

    public static NestedPasswordFindFragment newInstance() {
        return new NestedPasswordFindFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_password_find, container, false);

        PasswordFindViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(PasswordFindViewModel.class);
        binding.setPasswordFindViewModel(viewModel);

        Runnable onSuccessCheck = this::onSuccessCheck;
        Runnable onInvalid = this::onInvalid;
        Runnable onError = this::onError;

        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestPasswordFind(onSuccessCheck, onInvalid, onError);
            }
        });

        Runnable onPasswordMatch = this::onPasswordMatch;
        Runnable onPasswordUnMatch = this::onPasswordUnMatch;
        Runnable onPasswordTooShort = this::onPasswordTooShort;
        Runnable onSuccessChange = this::onSuccessChanged;
        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.validate(onPasswordMatch, onPasswordUnMatch, onPasswordTooShort)) {
                    viewModel.requestPasswordChange(onSuccessChange ,onError);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSuccessCheck() {
        binding.check.setEnabled(false);
        binding.emailLayout.setErrorEnabled(false);
        binding.nameLayout.setErrorEnabled(false);
        binding.phoneLayout.setErrorEnabled(false);
        binding.emailLayout.setError(null);
        binding.nameLayout.setError(null);
        binding.phoneLayout.setError(null);
        binding.emailLayout.setEnabled(false);
        binding.nameLayout.setEnabled(false);
        binding.phoneLayout.setEnabled(false);
        binding.resultGroup.setVisibility(View.VISIBLE);
    }

    public void onInvalid() {
        binding.emailLayout.setErrorEnabled(false);
        binding.nameLayout.setErrorEnabled(false);
        binding.phoneLayout.setErrorEnabled(false);
        binding.emailLayout.setError("정보가 일치하는 사용자를 찾을 수 없습니다.");
        binding.nameLayout.setError("정보가 일치하는 사용자를 찾을 수 없습니다.");
        binding.phoneLayout.setError("정보가 일치하는 사용자를 찾을 수 없습니다.");
    }

    public void onPasswordMatch() {
        binding.passwordLayout.setErrorEnabled(false);
        binding.passwordCheckLayout.setErrorEnabled(false);
        binding.passwordLayout.setError(null);
        binding.passwordCheckLayout.setError(null);
    }

    public void onPasswordUnMatch() {
        binding.passwordLayout.setErrorEnabled(true);
        binding.passwordCheckLayout.setErrorEnabled(true);
        binding.passwordLayout.setError("비밀번호가 일치하지 않습니다.");
        binding.passwordCheckLayout.setError("비밀번호가 일치하지 않습니다.");
    }

    public void onPasswordTooShort() {
        binding.passwordLayout.setErrorEnabled(true);
        binding.passwordCheckLayout.setErrorEnabled(true);
        binding.passwordLayout.setError("비밀번호가 너무 짧습니다. (8자 이상)");
        binding.passwordCheckLayout.setError("비밀번호가 너무 짧습니다. (8자 이상)");
    }

    public void onError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }

    public void onSuccessChanged() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_forgotFragment_to_passwordChangedDialog);
    }
}