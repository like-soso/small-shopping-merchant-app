package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentReportDoneBinding;

public class ReportDoneFragment extends Fragment {

    FragmentReportDoneBinding binding;

    public ReportDoneFragment() {

    }

    public static SignupDoneFragment newInstance() {
        return new SignupDoneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_done, container, false);

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}
