package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentReportBinding;
import com.sososhopping.merchant.viewmodel.ReportViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String CUSTOMERID = "customerId";
    private static final String CUSTOMERNAME = "customerName";

    private int storeId;
    private int customerId;
    private String customerName;

    FragmentReportBinding binding;

    public ReportFragment() {

    }

    public static ReportFragment newInstance(int storeId, int customerId, String customerName) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putInt(CUSTOMERID, customerId);
        args.putString(CUSTOMERNAME, customerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            customerId = getArguments().getInt(CUSTOMERID);
            customerName = getArguments().getString(CUSTOMERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false);

        ReportViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(ReportViewModel.class);
        binding.setReportViewModel(viewModel);

        binding.userNameLayout.getEditText().setText(customerName);

        Runnable onSuccess = this::onSuccess;

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.reportReport) {
                    viewModel.requestReport(storeId, customerId, customerName, onSuccess);
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    public void onSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_reportFragment_to_reportDoneFragment);
    }
}