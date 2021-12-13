package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentItemBinding;
import com.sososhopping.merchant.model.item.entity.Item;
import com.sososhopping.merchant.model.item.repository.ItemRepository;
import com.sososhopping.merchant.view.adapter.ItemListRecyclerViewAdapter;

import java.util.List;
import java.util.function.Consumer;

public class ItemFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentItemBinding binding;

    public ItemFragment() {

    }

    public static ItemFragment newInstance(int storeId) {
        ItemFragment fragment = new ItemFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_item, container, false);

        Consumer<List<Item>> onItemListAcquired = this::onItemListAcquired;
        Runnable onFailed = this::onNetworkError;

        ItemRepository.getInstance().requestItemList(storeId, onItemListAcquired, onFailed);

        binding.shopListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                Navigation.findNavController(v).navigate(R.id.action_itemFragment_to_itemRegisterFragment, bundle);
            }
        });

        return binding.getRoot();
    }

    private void onItemListAcquired(List<Item> itemList) {
        binding.shopListRecyclerView.setAdapter(new ItemListRecyclerViewAdapter(itemList));
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
