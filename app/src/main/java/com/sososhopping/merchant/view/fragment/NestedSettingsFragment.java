package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.NestedFragmentSettingsBinding;
import com.sososhopping.merchant.utils.Constant;
import com.sososhopping.merchant.utils.sharedpreferences.SharedPreferenceManager;
import com.sososhopping.merchant.utils.token.TokenStore;

public class NestedSettingsFragment extends Fragment {

    NestedFragmentSettingsBinding binding;

    public NestedSettingsFragment() {

    }

    public static NestedSettingsFragment newInstance() {
        return new NestedSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.nested_fragment_settings, container, false);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenStore.storeAuthToken("");
                TokenStore.storeFirebaseToken("");
                SharedPreferenceManager.setString(getContext(), Constant.SHARED_PREFERENCE_KEY_ID, Constant.SHARED_PREFERENCE_DEFAULT_STRING);
                SharedPreferenceManager.setString(getContext(), Constant.SHARED_PREFERENCE_KEY_PASSWORD, Constant.SHARED_PREFERENCE_DEFAULT_STRING);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_mainFragment_to_startFragment);
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
