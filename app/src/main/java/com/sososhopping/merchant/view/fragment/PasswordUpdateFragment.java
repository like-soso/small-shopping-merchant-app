package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentPasswordUpdateBinding;
import com.sososhopping.merchant.viewmodel.PasswordUpdateViewModel;

public class PasswordUpdateFragment extends Fragment {

    FragmentPasswordUpdateBinding binding;

    public PasswordUpdateFragment() {

    }

    public static PasswordUpdateFragment newInstance() {
        return new PasswordUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_update, container, false);

        PasswordUpdateViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(PasswordUpdateViewModel.class);
        binding.setPasswordUpdateViewModel(viewModel);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        Runnable onPasswordMatch = this::onPasswordMatch;
        Runnable onPasswordUnMatch = this::onPasswordUnMatch;
        Runnable onPasswordTooShort = this::onPasswordTooShort;

        Runnable onSuccess = this::onSuccessChanged;
        Runnable onInvalid = this::onInvalid;
        Runnable onError = this::onError;

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.passwordUpdate) {
                    if (viewModel.validate(onPasswordMatch, onPasswordUnMatch, onPasswordTooShort)) {
                        viewModel.requestPasswordUpdate(onSuccess, onInvalid, onError);
                    }
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onPasswordMatch() {
        binding.newPasswordLayout.setErrorEnabled(false);
        binding.newPasswordCheckLayout.setErrorEnabled(false);
        binding.newPasswordLayout.setError(null);
        binding.newPasswordCheckLayout.setError(null);
    }

    public void onPasswordUnMatch() {
        binding.newPasswordLayout.setErrorEnabled(true);
        binding.newPasswordCheckLayout.setErrorEnabled(true);
        binding.newPasswordLayout.setError("비밀번호가 일치하지 않습니다.");
        binding.newPasswordCheckLayout.setError("비밀번호가 일치하지 않습니다.");
    }

    public void onPasswordTooShort() {
        binding.newPasswordLayout.setErrorEnabled(true);
        binding.newPasswordCheckLayout.setErrorEnabled(true);
        binding.newPasswordLayout.setError("비밀번호가 너무 짧습니다. (8자 이상)");
        binding.newPasswordCheckLayout.setError("비밀번호가 너무 짧습니다. (8자 이상)");
    }

    public void onError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }

    public void onSuccessChanged() {
        NavHostFragment.findNavController(this).navigate(R.id.action_passwordUpdateFragment_to_passwordChangedDialog);
    }

    public void onInvalid() {
        binding.passwordLayout.setErrorEnabled(true);
        binding.passwordLayout.setError("비밀번호가 일치하지 않습니다.");
    }
}