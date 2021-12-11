package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentStoreManageBinding;

public class StoreManageFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String STORENAME = "storeName";

    private int storeId;
    private String storeName;

    int currentItem;

    FragmentStoreManageBinding binding;

    public StoreManageFragment() {

    }

    public static StoreManageFragment newInstance(int storeId, String storeName) {
        StoreManageFragment fragment = new StoreManageFragment();
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
        currentItem = R.id.storeManageConsole;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_manage, container, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        Bundle bundle = new Bundle();
        bundle.putInt(STOREID, storeId);
        bundle.putString(STORENAME, storeName);

        try {
            navController.getGraph();
        } catch (Exception e) {
            navController.setGraph(R.navigation.nav_graph_nested_store_manage, bundle);
        }

        binding.toolbarTitle.setText(storeName);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.storeManageConsole) {
                    if (currentItem == R.id.storeManageChat) {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedChatroomFragment_to_nestedConsoleFragment, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedCalendarOrderListFragment_to_nestedConsoleFragment, bundle);
                    }
                    currentItem = R.id.storeManageConsole;
                } else if (item.getItemId() == R.id.storeManageChat) {
                    if (currentItem == R.id.storeManageConsole) {
                        Log.d("어디서 터지나", "여긴가?");
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedConsoleFragment_to_nestedChatroomFragment, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedCalendarOrderListFragment_to_nestedChatroomFragment, bundle);
                    }
                    currentItem = R.id.storeManageChat;
                } else {
                    if (currentItem ==R.id.storeManageConsole) {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedConsoleFragment_to_nestedCalendarOrderListFragment, bundle);
                    } else {
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_nestedChatroomFragment_to_nestedCalendarOrderListFragment, bundle);
                    }
                    currentItem = R.id.calendar;
                }
                return true;
            }
        });

        binding.bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                ;
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