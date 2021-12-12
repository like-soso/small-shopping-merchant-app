package com.sososhopping.merchant.view.fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentCouponRegisterBinding;
import com.sososhopping.merchant.viewmodel.CouponRegisterViewModel;

import java.util.Calendar;

public class CouponRegisterFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentCouponRegisterBinding binding;

    public CouponRegisterFragment() {

    }

    public static CouponRegisterFragment newInstance(int storeId) {
        CouponRegisterFragment fragment = new CouponRegisterFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon_register, container, false);

        CouponRegisterViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(CouponRegisterViewModel.class);
        binding.setCouponReigsterViewModel(viewModel);

        Runnable onSuccess = this::onSuccess;

        Runnable onNameEmpty = this::onNameEmpty;
        Runnable onNameNotEmpty = this::onNameNotEmpty;
        Runnable onAmountEmpty = this::onAmountEmpty;
        Runnable onAmountNotEmpty = this::onAmountNotEmpty;
        Runnable onAmountInvalid = this::onAmountInvalid;
        Runnable onMinPriceEmpty = this::onMinPriceEmpty;
        Runnable onMinPriceNotEmpty = this::onMinPriceNotEmpty;
        Runnable onMinPriceInvalid = this::onMinPriceInvalid;
        Runnable onQuantityEmpty = this::onQuantityEmpty;
        Runnable onQuantityNotEmpty = this::onQuantityNotEmpty;
        Runnable onQuantityInvalid = this::onQuantityInvalid;
        Runnable onExpiryEmpty = this::onExpiryEmpty;
        Runnable onExpiryNotEmpty = this::onExpiryNotEmpty;
        Runnable onIssuedStartEmpty = this::onIssuedStartEmpty;
        Runnable onIssuedStartNotEmpty = this::onIssuedStartNotEmpty;
        Runnable onIssuedEndEmpty = this::onIssuedEndEmpty;
        Runnable onIssuedEndNotEmpty = this::onIssuedEndNotEmpty;

        binding.shopListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.couponRegister) {
                    if (viewModel.valid(
                            onNameEmpty,
                            onNameNotEmpty,
                            onAmountEmpty,
                            onAmountNotEmpty,
                            onAmountInvalid,
                            onMinPriceEmpty,
                            onMinPriceNotEmpty,
                            onMinPriceInvalid,
                            onQuantityEmpty,
                            onQuantityNotEmpty,
                            onQuantityInvalid,
                            onExpiryEmpty,
                            onExpiryNotEmpty,
                            onIssuedStartEmpty,
                            onIssuedStartNotEmpty,
                            onIssuedEndEmpty,
                            onIssuedEndNotEmpty
                    )) {
                        viewModel.requestRegister(storeId, onSuccess);
                    }
                }
                return true;
            }
        });

        binding.shopListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
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

    public void onNameEmpty() {
        binding.couponNameLayout.setErrorEnabled(true);
        binding.couponNameLayout.setError("필수 입력 항목입니다.");
    }

    public void onNameNotEmpty() {
        binding.couponNameLayout.setErrorEnabled(false);
        binding.couponNameLayout.setError(null);
    }

    public void onAmountEmpty() {
        binding.couponAmountLayout.setErrorEnabled(true);
        binding.couponAmountLayout.setError("필수 입력 항목입니다.");
    }

    public void onAmountNotEmpty() {
        binding.couponAmountLayout.setErrorEnabled(false);
        binding.couponAmountLayout.setError(null);
    }

    public void onAmountInvalid() {
        binding.couponAmountLayout.setErrorEnabled(true);
        binding.couponAmountLayout.setError("유효하지 않은 입력입니다.");
    }

    public void onMinPriceEmpty() {
        binding.couponMinPriceLayout.setErrorEnabled(true);
        binding.couponMinPriceLayout.setError("필수 입력 항목입니다.");
    }

    public void onMinPriceNotEmpty() {
        binding.couponMinPriceLayout.setErrorEnabled(false);
        binding.couponMinPriceLayout.setError(null);
    }

    public void onMinPriceInvalid() {
        binding.couponMinPriceLayout.setErrorEnabled(true);
        binding.couponMinPriceLayout.setError("유효하지 않은 입력입니다.");
    }

    public void onQuantityEmpty() {
        binding.couponQuantityLayout.setErrorEnabled(true);
        binding.couponQuantityLayout.setError("필수 입력 항목입니다.");
    }

    public void onQuantityNotEmpty() {
        binding.couponQuantityLayout.setErrorEnabled(false);
        binding.couponQuantityLayout.setError(null);
    }

    public void onQuantityInvalid() {
        binding.couponQuantityLayout.setErrorEnabled(true);
        binding.couponQuantityLayout.setError("유효하지 않은 입력입니다.");
    }

    public void onExpiryEmpty() {
        binding.couponExpiryLayout.setErrorEnabled(true);
        binding.couponExpiryLayout.setError("필수 입력 항목입니다.");
    }

    public void onExpiryNotEmpty() {
        binding.couponExpiryLayout.setErrorEnabled(false);
        binding.couponExpiryLayout.setError(null);
    }

    public void onIssuedStartEmpty() {
        binding.couponIssueStartLayout.setErrorEnabled(true);
        binding.couponIssueStartLayout.setError("필수 입력 항목입니다.");
    }

    public void onIssuedStartNotEmpty() {
        binding.couponIssueStartLayout.setErrorEnabled(false);
        binding.couponIssueStartLayout.setError(null);
    }

    public void onIssuedEndEmpty() {
        binding.couponIssueEndLayout.setErrorEnabled(true);
        binding.couponIssueEndLayout.setError("필수 입력 항목입니다.");
    }

    public void onIssuedEndNotEmpty() {
        binding.couponIssueEndLayout.setErrorEnabled(false);
        binding.couponIssueEndLayout.setError(null);
    }

    public void onSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_couponRegisterFragment_to_couponRegisteredFragment);
    }
}