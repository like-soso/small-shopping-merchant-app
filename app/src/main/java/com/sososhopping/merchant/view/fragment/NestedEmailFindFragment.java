package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedEmailFindBinding;
import com.sososhopping.merchant.viewmodel.EmailFindViewModel;

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

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}