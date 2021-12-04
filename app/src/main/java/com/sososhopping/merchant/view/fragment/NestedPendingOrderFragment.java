package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedPendingOrderBinding;
import com.sososhopping.merchant.model.order.entity.Order;
import com.sososhopping.merchant.model.order.repository.OrderRepository;
import com.sososhopping.merchant.view.adapter.PendingOrderListRecyclerViewAdapter;

import java.util.List;
import java.util.function.Consumer;

public class NestedPendingOrderFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    private final String type = "PENDING";

    FragmentNestedPendingOrderBinding binding;

    public NestedPendingOrderFragment() {

    }

    public static NestedPendingOrderFragment newInstance(int storeId) {
        NestedPendingOrderFragment fragment = new NestedPendingOrderFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_pending_order, container, false);

        Consumer<List<Order>> onListAcquired = this::onListAcquired;
        Runnable onError = this::onNetworkError;

        OrderRepository.getInstance().requestOrderList(storeId, type, onListAcquired, onError);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onListAcquired(List<Order> orderList) {
        binding.recyclerView.setAdapter(new PendingOrderListRecyclerViewAdapter(orderList));
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}