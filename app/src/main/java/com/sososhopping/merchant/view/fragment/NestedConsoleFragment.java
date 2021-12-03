package com.sososhopping.merchant.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedConsoleBinding;
import com.sososhopping.merchant.model.store.dto.response.StoreOpenStatusResponseDto;
import com.sososhopping.merchant.model.store.repository.StoreRepository;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.function.Consumer;

public class NestedConsoleFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String STORENAME = "storeName";

    private int storeId;
    private String storeName;

    FragmentNestedConsoleBinding binding;

    Resources resources;

    public NestedConsoleFragment() {

    }

    public static NestedConsoleFragment newInstance(int storeId) {
        NestedConsoleFragment fragment = new NestedConsoleFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
        }
        resources = getResources();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_console, container, false);

        Consumer<StoreOpenStatusResponseDto> onStatusChecked = this::onBusinessStatusChecked;
        Runnable onError = this::onNetworkError;

        StoreRepository.getInstance().requestStoreBusinessStatus(storeId, onStatusChecked, onError);

        binding.storeOpenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreRepository.getInstance().requestStoreBusinessStatusUpdate(storeId, onStatusChecked, onError);
            }
        });

        return binding.getRoot();
    }

    private void onBusinessStatusChecked(StoreOpenStatusResponseDto dto) {
        boolean result = dto.getOpenStatus();

        if (result) {
            binding.storeOpenImg.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_pause_24));
            binding.storeOpenText.setText("영업 중지");
        } else {
            binding.storeOpenImg.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_arrow_24));
            binding.storeOpenText.setText("영업 재개");
        }
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}
