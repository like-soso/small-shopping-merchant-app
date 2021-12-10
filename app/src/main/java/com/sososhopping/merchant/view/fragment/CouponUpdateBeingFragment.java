package com.sososhopping.merchant.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentCouponUpdateBeingBinding;
import com.sososhopping.merchant.model.coupon.entity.Coupon;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;
import com.sososhopping.merchant.viewmodel.CouponUpdateBeingViewModel;

import java.util.Calendar;
import java.util.function.Consumer;

public class CouponUpdateBeingFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String COUPONID = "couponId";

    private int storeId;
    private int couponId;

    FragmentCouponUpdateBeingBinding binding;
    CouponUpdateBeingViewModel viewModel;

    public CouponUpdateBeingFragment() {

    }

    public static CouponUpdateBeingFragment newInstance(int storeId, int couponId) {
        CouponUpdateBeingFragment fragment = new CouponUpdateBeingFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putInt(COUPONID, couponId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            couponId = getArguments().getInt(COUPONID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon_update_being, container, false);

        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(CouponUpdateBeingViewModel.class);
        binding.setCouponReigsterViewModel(viewModel);

        Runnable onSuccess = this::navigateUp;
        Consumer<Coupon> onSuccessRead = this::onSuccessRead;

        CouponRepository.getInstance().requestCoupon(storeId, couponId, onSuccessRead);

        binding.shopListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.couponUpdateBeing) viewModel.requestRegister(storeId, couponId, onSuccess);
                return true;
            }
        });

        binding.typeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rate) {
                    viewModel.setCouponType("RATE");
                    binding.type.setText("%");
                    binding.couponAmountLayout.setHint("할인 비율");
                } else {
                    viewModel.setCouponType("FIX");
                    binding.type.setText("원");
                    binding.couponAmountLayout.setHint("할인 금액");
                }
            }
        });

        binding.couponIssueStartLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                DatePickerDialog mDatePiceker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String yearString = Integer.toString(year);
                        String monthString = Integer.toString(month + 1);
                        if (monthString.length() < 2) {
                            monthString = "0" + monthString;
                        }
                        String dayString = Integer.toString(dayOfMonth);
                        if (dayString.length() < 2) {
                            dayString = "0" + dayString;
                        }
                        String date = yearString + "/" + monthString + "/" + dayString;
                        binding.couponIssueStartLayout.getEditText().setText(date);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePiceker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePiceker.show();
            }
        });

        binding.couponIssueEndLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                DatePickerDialog mDatePiceker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String yearString = Integer.toString(year);
                        String monthString = Integer.toString(month + 1);
                        if (monthString.length() < 2) {
                            monthString = "0" + monthString;
                        }
                        String dayString = Integer.toString(dayOfMonth);
                        if (dayString.length() < 2) {
                            dayString = "0" + dayString;
                        }
                        String date = yearString + "/" + monthString + "/" + dayString;
                        binding.couponIssueEndLayout.getEditText().setText(date);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePiceker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePiceker.show();
            }
        });

        binding.couponExpiryLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                DatePickerDialog mDatePiceker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String yearString = Integer.toString(year);
                        String monthString = Integer.toString(month + 1);
                        if (monthString.length() < 2) {
                            monthString = "0" + monthString;
                        }
                        String dayString = Integer.toString(dayOfMonth);
                        if (dayString.length() < 2) {
                            dayString = "0" + dayString;
                        }
                        String date = yearString + "/" + monthString + "/" + dayString;
                        binding.couponExpiryLayout.getEditText().setText(date);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePiceker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePiceker.show();
            }
        });

        return binding.getRoot();
    }

    public void navigateUp() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    public void onSuccessRead(Coupon coupon) {
        if (coupon.getCouponType().equals("FIX")) {
            binding.type.setText("원");
            binding.couponAmountLayout.setHint("할인 금액");
        }
        else {
            binding.type.setText("%");
            binding.couponAmountLayout.setHint("할인 비율");
        }
        viewModel.fromModel(coupon);
    }
}