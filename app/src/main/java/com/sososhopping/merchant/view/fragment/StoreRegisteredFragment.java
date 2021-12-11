package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentStoreRegisteredBinding;

public class StoreRegisteredFragment extends Fragment {

    public StoreRegisteredFragment() {

    }

    public static StoreRegisteredFragment newInstance() {
        return new StoreRegisteredFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStoreRegisteredBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_registered, container, false);

        binding.signupDoneOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        return binding.getRoot();
    }
}