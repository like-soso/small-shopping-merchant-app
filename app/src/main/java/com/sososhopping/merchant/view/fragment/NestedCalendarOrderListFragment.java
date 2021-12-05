package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentNestedCalendarOrderListBinding;
import com.sososhopping.merchant.model.order.dto.response.OrderListResponseDto;
import com.sososhopping.merchant.model.order.repository.OrderRepository;
import com.sososhopping.merchant.view.adapter.CalendarOrderListRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;

public class NestedCalendarOrderListFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    private String date;

    FragmentNestedCalendarOrderListBinding binding;

    public NestedCalendarOrderListFragment() {

    }

    public static NestedCalendarOrderListFragment newInstance(int storeId) {
        NestedCalendarOrderListFragment fragment = new NestedCalendarOrderListFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nested_calendar_order_list, container, false);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        date = df.format(new Date(binding.calendar.getDate()));
        System.out.println(date);

        Consumer<OrderListResponseDto> onListAcquired = this::onListAcquired;
        Runnable onError = this::onNetworkError;

        OrderRepository.getInstance().requestOrderListAt(storeId, date, onListAcquired, onError);

        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthString;
                String dayString;
                if ((month + 1) < 10) {
                    monthString = "0" + (month + 1);
                } else {
                    monthString = "" + (month + 1);
                }

                if (dayOfMonth < 10) {
                    dayString = "0" + dayOfMonth;
                } else {
                    dayString = "" + dayOfMonth;
                }
                date = year + "-" + monthString + "-" + dayString;
                System.out.println(date);
                OrderRepository.getInstance().requestOrderListAt(storeId, date, onListAcquired, onError);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onListAcquired(OrderListResponseDto dto) {
        System.out.println(dto.getOrders().size());
        binding.recyclerView.setAdapter(new CalendarOrderListRecyclerViewAdapter(dto.getOrders()));
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}