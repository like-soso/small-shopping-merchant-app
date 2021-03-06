package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentSignupFormBinding;
import com.sososhopping.merchant.viewmodel.SignupViewModel;

public class SignupFormFragment extends Fragment {

    FragmentSignupFormBinding binding;

    public SignupFormFragment() {

    }

    public static SignupFormFragment newInstance() {
        return new SignupFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_form, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());

        SignupViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(SignupViewModel.class);
        binding.setSignupFormViewModel(viewModel);

        Runnable onNetworkError = this::onNetworkError;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        Runnable onInappropriateEmail = this::onInappropriateEmail;
        Runnable onEmailNotDuplicated = this::onEmailNotDuplicated;
        Runnable onDuplicateEmail = this::onDuplicateEmail;

        binding.emailDuplicationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestEmailDupCheck(onInappropriateEmail,
                        onEmailNotDuplicated,
                        onDuplicateEmail,
                        onNetworkError);
            }
        });

        Runnable onPasswordProper = this::onPasswordProper;
        Runnable onPasswordMatch = this::onPasswordMatch;
        Runnable onNameProper = this::onNameProper;
        Runnable onPhoneProper = this::onPhoneProper;
        Runnable onEmailNotChecked = this::onEmailNotChecked;
        Runnable onPasswordTooShort = this::onPasswordTooShort;
        Runnable onPasswordCheckEmpty = this::onPasswordCheckEmpty;
        Runnable onPasswordNotMatched = this::onPasswordNotMatched;
        Runnable onNameEmpty = this::onNameEmpty;
        Runnable onPhoneEmpty = this::onPhoneEmpty;

        Runnable onSignupSuccess = this::onSignupSuccess;

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.signupFormSignup) {
                    if (viewModel.validateForm(
                            onPasswordProper,
                            onPasswordMatch,
                            onNameProper,
                            onPhoneProper,
                            onEmailNotChecked,
                            onPasswordTooShort,
                            onPasswordCheckEmpty,
                            onPasswordNotMatched,
                            onNameEmpty,
                            onPhoneEmpty)){
                        viewModel.requestSignup(
                                onSignupSuccess,
                                onNetworkError
                        );
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

    public void onSignupSuccess() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signupFormFragment_to_signupDoneFragment);
    }

    public void onEmailNotDuplicated() {
        TextInputLayout emailLayout = binding.emailLayout;
        TextView emailDupChecker = binding.emailDuplicationCheck;
        emailLayout.setEnabled(false);
        emailDupChecker.setText(R.string.signup_form_after_check_email_duplication);
        emailDupChecker.setTextColor(getResources().getColor(R.color.positive));
        emailDupChecker.setClickable(false);
    }

    public void onPasswordProper() {
        TextInputLayout passwordLayout = binding.passwordLayout;
        passwordLayout.setErrorEnabled(false);
        passwordLayout.setError(null);
    }

    public void onPasswordMatch() {
        TextInputLayout passwordCheckLayout = binding.passwordCheckLayout;
        passwordCheckLayout.setErrorEnabled(false);
        passwordCheckLayout.setError(null);
    }

    public void onNameProper() {
        TextInputLayout nameLayout = binding.nameLayout;
        nameLayout.setErrorEnabled(false);
        nameLayout.setError(null);
    }

    public void onPhoneProper() {
        TextInputLayout phoneLayout = binding.phoneLayout;
        phoneLayout.setErrorEnabled(false);
        phoneLayout.setError(null);
    }

    public void onInappropriateEmail() {
        TextInputLayout emailLayout = binding.emailLayout;
        emailLayout.setErrorEnabled(true);
        emailLayout.setError(getString(R.string.signup_form_error_email_inappropriate));
    }

    public void onDuplicateEmail() {
        TextInputLayout emailLayout = binding.emailLayout;
        emailLayout.setErrorEnabled(true);
        emailLayout.setError(getString(R.string.signup_form_error_email_duplicated));
    }

    public void onEmailNotChecked() {
        TextInputLayout emailLayout = binding.emailLayout;
        emailLayout.setErrorEnabled(true);
        emailLayout.setError(getString(R.string.signup_form_error_email_not_checked));
    }

    public void onPasswordTooShort() {
        TextInputLayout passwordLayout = binding.passwordLayout;
        passwordLayout.setErrorEnabled(true);
        passwordLayout.setError(getString(R.string.signup_form_error_password_too_short));
    }

    public void onPasswordNotMatched() {
        TextInputLayout passwordCheckLayout = binding.passwordCheckLayout;
        passwordCheckLayout.setErrorEnabled(true);
        passwordCheckLayout.setError(getString(R.string.signup_form_error_password_check_mismatch));
    }

    public void onPasswordCheckEmpty() {
        TextInputLayout passwordCheckLayout = binding.passwordCheckLayout;
        passwordCheckLayout.setErrorEnabled(true);
        passwordCheckLayout.setError(getString(R.string.signup_form_error_required));
    }

    public void onNameEmpty() {
        TextInputLayout nameLayout = binding.nameLayout;
        nameLayout.setErrorEnabled(true);
        nameLayout.setError(getString(R.string.signup_form_error_required));
    }

    public void onPhoneEmpty() {
        TextInputLayout phoneLayout = binding.phoneLayout;
        phoneLayout.setErrorEnabled(true);
        phoneLayout.setError(getString(R.string.signup_form_error_required));
    }

    public void onNetworkError() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_networkErrorDialog);
    }
}
