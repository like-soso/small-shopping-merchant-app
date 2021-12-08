package com.sososhopping.merchant.view.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.DialogAccountingRegisterBinding;
import com.sososhopping.merchant.viewmodel.AccountingRegisterViewModel;

import java.util.Calendar;

public class AccountingRegisterDialog extends DialogFragment {

    private static final String STOREID = "storeId";

    private int storeId;

    DialogAccountingRegisterBinding binding;

    public AccountingRegisterDialog() {

    }

    public AccountingRegisterDialog newInstance() {
        AccountingRegisterDialog dialog = new AccountingRegisterDialog();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_accounting_register, container, false);

        AccountingRegisterViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(AccountingRegisterViewModel.class);
        binding.setAccountingRegisterViewModel(viewModel);

        Runnable onSuccess = this::closeDialog;
        Runnable onError = this::onNetworkError;

        Runnable onMemoEmpty = this::onMemoEmpty;
        Runnable onMemoNotEmpty = this::onMemoNotEmpty;
        Runnable onAmountEmpty = this::onAmountEmpty;
        Runnable onAmountNegative = this::onAmountNegative;
        Runnable onAmountNotEmpty = this::onAmountNotEmpty;
        Runnable onDateEmpty = this::onDateEmpty;
        Runnable onDateNotEmpty = this::onDateNotEmpty;
        Runnable onTimeEmpty = this::onTimeEmpty;
        Runnable onTimeNotEmpty = this::onTimeNotEmpty;

        binding.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.validate(
                        onMemoEmpty,
                        onMemoNotEmpty,
                        onAmountEmpty,
                        onAmountNegative,
                        onAmountNotEmpty,
                        onDateEmpty,
                        onDateNotEmpty,
                        onTimeEmpty,
                        onTimeNotEmpty
                )) viewModel.requestRegister(storeId, onSuccess, onError);
            }
        });

        binding.date.getEditText().setOnClickListener(new View.OnClickListener() {
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
                        binding.date.getEditText().setText(date);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePiceker.show();
            }
        });

        binding.time.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hourString;
                        if (selectedHour < 10) {
                            hourString = "0";
                        } else hourString ="";

                        hourString += Integer.toString(selectedHour);

                        String minuteString;
                        if (selectedMinute < 10) {
                            minuteString = "0";
                        } else minuteString ="";

                        minuteString += Integer.toString(selectedMinute);

                        String time = hourString + ":" + minuteString;
                        binding.time.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewModel.setIsIncome(checkedId != R.id.expenditure);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getParentFragmentManager().setFragmentResult("key", null);
    }

    public void onMemoEmpty() {
        binding.memo.setErrorEnabled(true);
        binding.memo.setError("필수 입력 항목입니다.");
    }

    public void onMemoNotEmpty() {
        binding.memo.setErrorEnabled(false);
        binding.memo.setError(null);
    }

    public void onAmountEmpty() {
        binding.amount.setErrorEnabled(true);
        binding.amount.setError("필수 입력 항목입니다.");
    }

    public void onAmountNegative() {
        binding.amount.setErrorEnabled(true);
        binding.amount.setError("금액은 양수만 입력 가능합니다.");
    }

    public void onAmountNotEmpty() {
        binding.amount.setErrorEnabled(false);
        binding.amount.setError(null);
    }

    public void onDateEmpty() {
        binding.date.setErrorEnabled(true);
        binding.date.setError("필수 입력 항목입니다.");
    }

    public void onDateNotEmpty() {
        binding.date.setErrorEnabled(false);
        binding.date.setError(null);
    }

    public void onTimeEmpty() {
        binding.time.setErrorEnabled(true);
        binding.time.setError("필수 입력 항목입니다.");
    }

    public void onTimeNotEmpty() {
        binding.time.setErrorEnabled(false);
        binding.time.setError(null);
    }

    public void closeDialog() {
        this.dismiss();
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
