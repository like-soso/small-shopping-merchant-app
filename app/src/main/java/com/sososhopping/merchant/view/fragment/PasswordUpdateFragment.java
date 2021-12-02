package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentPasswordUpdateBinding;
import com.sososhopping.merchant.viewmodel.PasswordUpdateViewModel;

public class PasswordUpdateFragment extends Fragment {

    //TODO: 백엔드 개발 요청 및 통합

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}