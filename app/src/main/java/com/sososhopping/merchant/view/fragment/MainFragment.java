package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        binding.bottomNavigationView.getMenu().findItem(R.id.mainShopList).setEnabled(false);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.mainShopList){
                    Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedSettingsFragment_to_nestedShopListFragment);
                    binding.bottomNavigationView.getMenu().findItem(R.id.mainShopList).setEnabled(false);
                    binding.bottomNavigationView.getMenu().findItem(R.id.mainSettings).setEnabled(true);
                } else{
                    Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedShopListFragment_to_nestedSettingsFragment);
                    binding.bottomNavigationView.getMenu().findItem(R.id.mainShopList).setEnabled(true);
                    binding.bottomNavigationView.getMenu().findItem(R.id.mainSettings).setEnabled(false);
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
}
