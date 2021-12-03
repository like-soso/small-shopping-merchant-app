package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentForgotBinding;

public class ForgotFragment extends Fragment {

    //TODO: 백엔드 개발 요청 및 통합

    FragmentForgotBinding binding;

    public ForgotFragment() {

    }

    public static ForgotFragment newInstance() {
        return new ForgotFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot, container, false);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.parent.getSelectedTabPosition() == 0) {
                    Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedPasswordFindFragment_to_nestedEmailFindFragment);
                } else {
                    Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedEmailFindFragment_to_nestedPasswordFindFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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