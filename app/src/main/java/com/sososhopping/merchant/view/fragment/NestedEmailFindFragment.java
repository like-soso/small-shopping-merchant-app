package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedEmailFindBinding;
import com.sososhopping.merchant.model.auth.dto.response.EmailFindResponseDto;
import com.sososhopping.merchant.viewmodel.EmailFindViewModel;

import java.util.function.Consumer;

public class NestedEmailFindFragment extends Fragment {

    FragmentNestedEmailFindBinding binding;

    public NestedEmailFindFragment() {

    }

    public static NestedEmailFindFragment newInstance() {
        return new NestedEmailFindFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_email_find, container, false);

        EmailFindViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(EmailFindViewModel.class);
        binding.setEmailFindViewModel(viewModel);

        Consumer<EmailFindResponseDto> onSuccess = this::onSuccess;
        Runnable onInvalid = this::onInvalid;
        Runnable onError = this::onError;

        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.requestEmailFind(onSuccess, onInvalid, onError);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSuccess(EmailFindResponseDto dto) {
        binding.nameLayout.setErrorEnabled(false);
        binding.phoneLayout.setErrorEnabled(false);
        binding.nameLayout.setError(null);
        binding.phoneLayout.setError(null);
        binding.email.setText(dto.getEmail());
        binding.resultGroup.setVisibility(View.VISIBLE);
    }

    public void onInvalid() {
        binding.nameLayout.setErrorEnabled(true);
        binding.phoneLayout.setErrorEnabled(true);
        binding.nameLayout.setError("사용자를 찾을 수 없습니다.");
        binding.phoneLayout.setError("사용자를 찾을 수 없습니다.");
    }

    public void onError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}