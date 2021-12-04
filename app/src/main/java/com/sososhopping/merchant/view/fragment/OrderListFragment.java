package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentOrderListBinding;

public class OrderListFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentOrderListBinding binding;

    int currentItem;

    public OrderListFragment() {

    }

    public static OrderListFragment newInstance(int storeId) {
        OrderListFragment fragment = new OrderListFragment();
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
        currentItem = R.id.orderListPending;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_list, container, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        Bundle bundle = new Bundle();
        bundle.putInt(STOREID, storeId);
        navController.setGraph(R.navigation.nav_graph_nested_order_list, bundle);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.orderListPending) {
                    if (currentItem == R.id.orderListPickup) {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedPickupOrderFragment_to_nestedPendingOrderFragment, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedDeliveryOrderList_to_nestedPendingOrderFragment, bundle);
                    }
                    currentItem = R.id.orderListPending;
                } else if (item.getItemId() == R.id.orderListPickup) {
                    if (currentItem == R.id.orderListPending) {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedPendingOrderFragment_to_nestedPickupOrderFragment, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedDeliveryOrderList_to_nestedPickupOrderFragment, bundle);
                    }
                    currentItem = R.id.orderListPickup;
                } else {
                    if (currentItem == R.id.orderListPending) {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedPendingOrderFragment_to_nestedDeliveryOrderList, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedPickupOrderFragment_to_nestedDeliveryOrderList, bundle);
                    }
                    currentItem = R.id.orderListDelivery;
                }
                return true;
            }
        });

        binding.bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

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