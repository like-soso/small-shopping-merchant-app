package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
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

import java.util.function.Consumer;

public class NestedConsoleFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String STORENAME = "storeName";

    private int storeId;
    private String storeName;

    FragmentNestedConsoleBinding binding;

    public NestedConsoleFragment() {

    }

    public static NestedConsoleFragment newInstance(int storeId, String storeName) {
        NestedConsoleFragment fragment = new NestedConsoleFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putString(STORENAME, storeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            storeName = getArguments().getString(STORENAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_console, container, false);

        System.out.println(storeId);

        Consumer<StoreOpenStatusResponseDto> onStatusChecked = this::onBusinessStatusChecked;
        Runnable onError = this::onNetworkError;

        binding.storeOpenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreRepository.getInstance().requestStoreBusinessStatusUpdate(storeId, onStatusChecked, onError);
            }
        });

        binding.orderListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_orderListFragment, bundle);
            }
        });

        binding.reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                bundle.putString(STORENAME, storeName);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_reviewListFragment, bundle);
            }
        });

        binding.accountingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_accountingListFragment, bundle);
            }
        });

        binding.pointLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_pointFragment, bundle);
            }
        });

        binding.couponLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_couponFragment, bundle);
            }
        });

        binding.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_itemFragment, bundle);
            }
        });

        binding.boardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_boardFragment, bundle);
            }
        });

        binding.storeInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_storeManageFragment_to_storeUpdateFragment, bundle);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Consumer<StoreOpenStatusResponseDto> onStatusChecked = this::onBusinessStatusChecked;
        Runnable onError = this::onNetworkError;
        StoreRepository.getInstance().requestStoreBusinessStatus(storeId, onStatusChecked, onError);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onBusinessStatusChecked(StoreOpenStatusResponseDto dto) {
        boolean result = dto.getOpenStatus();

        if (result) {
            binding.storeOpenImg.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_pause_24, null));
            binding.storeOpenText.setText(getString(R.string.console_store_pause));
        } else {
            binding.storeOpenImg.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_play_arrow_24, null));
            binding.storeOpenText.setText(getString(R.string.console_store_open));
        }
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}
