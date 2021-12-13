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
import com.sososhopping.merchant.databinding.FragmentUserUpdateBinding;
import com.sososhopping.merchant.model.auth.dto.response.ProfileResponseDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;
import com.sososhopping.merchant.viewmodel.PasswordUpdateViewModel;
import com.sososhopping.merchant.viewmodel.UserUpdateViewModel;

import java.util.function.Consumer;

public class UserUpdateFragment extends Fragment {

    FragmentUserUpdateBinding binding;
    UserUpdateViewModel viewModel;

    public UserUpdateFragment() {

    }

    public static UserUpdateFragment newInstance() {
        return new UserUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_update, container, false);

        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(UserUpdateViewModel.class);
        binding.setUserUpdateViewModel(viewModel);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        Runnable onSuccess = this::onSuccess;
        Runnable onInvalid = this::onInvalid;
        Runnable onPhoneDuplicate = this::onPhoneDuplicate;
        Runnable onError = this::onError;

        Runnable onNameEmpty = this::onNameEmpty;
        Runnable onNameNotEmpty = this::onNameNotEmpty;
        Runnable onPhoneEmpty = this::onPhoneEmpty;
        Runnable onPhoneNotEmpty = this::onPhoneNotEmpty;
        Consumer<ProfileResponseDto> onSuccessRead = this::onSuccessRead;

        AuthRepository.getInstance().requestMyProfile(onSuccessRead, onError);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.userUpdate) {
                    if (viewModel.valid(onNameEmpty, onNameNotEmpty, onPhoneEmpty, onPhoneNotEmpty)) {
                        viewModel.requestUpdate(onSuccess, onInvalid, onPhoneDuplicate, onError);
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

    public void onSuccessRead(ProfileResponseDto dto) {
        viewModel.setData(dto);
    }

    public void onInvalid() {
        binding.passwordCheckLayout.setErrorEnabled(true);
        binding.passwordCheckLayout.setError("비밀번호가 일치하지 않습니다.");
    }

    public void onNameEmpty() {
        binding.nameLayout.setErrorEnabled(true);
        binding.nameLayout.setError("필수 입력 항목입니다.");
    }

    public void onNameNotEmpty() {
        binding.nameLayout.setErrorEnabled(false);
        binding.nameLayout.setError(null);
    }

    public void onPhoneEmpty() {
        binding.phoneLayout.setErrorEnabled(true);
        binding.phoneLayout.setError("필수 입력 항목입니다.");
    }

    public void onPhoneNotEmpty() {
        binding.phoneLayout.setErrorEnabled(false);
        binding.phoneLayout.setError(null);
    }

    public void onSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_userUpdateFragment_to_profileUpdatedDialog);
    }

    public void onPhoneDuplicate() {
        binding.phoneLayout.setErrorEnabled(true);
        binding.phoneLayout.setError("이미 사용중인 휴대전화 번호입니다.");
    }

    public void onError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}