package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedStoreListBinding;
import com.sososhopping.merchant.model.store.entity.StoreBrief;
import com.sososhopping.merchant.model.store.repository.StoreRepository;
import com.sososhopping.merchant.view.adapter.StoreListRecyclerViewAdapter;

import java.util.List;
import java.util.function.Consumer;

public class NestedStoreListFragment extends Fragment {

    FragmentNestedStoreListBinding binding;

    public NestedStoreListFragment() {

    }

    public static NestedStoreListFragment newInstance() {
        return new NestedStoreListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_store_list, container, false);

        Consumer<List<StoreBrief>> onShopListAcquired = this::onShopListAcquired;
        Runnable onFailed = this::onNetworkError;

        StoreRepository.getInstance().requestStoreList(onShopListAcquired, onFailed);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onShopListAcquired(List<StoreBrief> storeBriefs) {
        binding.recyclerView.setAdapter(new StoreListRecyclerViewAdapter(storeBriefs));
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}
