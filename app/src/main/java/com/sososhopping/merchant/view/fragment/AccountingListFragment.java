package com.sososhopping.merchant.view.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentAccountingListBinding;
import com.sososhopping.merchant.model.accounting.entity.Accounting;
import com.sososhopping.merchant.model.accounting.repository.AccountingRepository;
import com.sososhopping.merchant.utils.token.TokenStore;
import com.sososhopping.merchant.view.adapter.AccountingListRecyclerViewAdapter;
import com.sososhopping.merchant.viewmodel.AccountingListViewModel;

import java.util.List;
import java.util.function.Consumer;

public class AccountingListFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentAccountingListBinding binding;

    public AccountingListFragment() {
        // Required empty public constructor
    }

    public static AccountingListFragment newInstance(int storeId) {
        AccountingListFragment fragment = new AccountingListFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounting_list, container, false);

        AccountingListViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(AccountingListViewModel.class);
        binding.setAccountingListViewModel(viewModel);

        Consumer<List<Accounting>> onItemListAcquired = this::onBoardListAcquired;
        Runnable onError = this::onNetworkError;

        AccountingRepository.getInstance().requestAccountingList(storeId, viewModel.getDateString(), onItemListAcquired, onError);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                Navigation.findNavController(v).navigate(R.id.action_accountingListFragment_to_accountingRegisterDialog, bundle);
            }
        });

        binding.shopAccountingDatePrevButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                viewModel.toPrevMonth();
                binding.invalidateAll();
                AccountingRepository.getInstance().requestAccountingList(storeId, viewModel.getDateString(), onItemListAcquired, onError);
            }
        });

        binding.shopAccountingDateNextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                viewModel.toNextMonth();
                binding.invalidateAll();
                AccountingRepository.getInstance().requestAccountingList(storeId, viewModel.getDateString(), onItemListAcquired, onError);
            }
        });

        getParentFragmentManager().setFragmentResultListener("key", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                AccountingRepository.getInstance().requestAccountingList(storeId, viewModel.getDateString(), onItemListAcquired, onError);
            }
        });

        return binding.getRoot();
    }

    public void onBoardListAcquired(List<Accounting> accountingList) {
        binding.shopListRecyclerView.setAdapter(new AccountingListRecyclerViewAdapter(accountingList));
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}