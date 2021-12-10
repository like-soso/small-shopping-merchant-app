package com.sososhopping.merchant.view.fragment;

import android.app.DatePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentStoreRegisterMetadataFormBinding;
import com.sososhopping.merchant.viewmodel.StoreRegisterViewModel;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class StoreRegisterMetadataFormFragment extends Fragment {

    FragmentStoreRegisterMetadataFormBinding binding;

    public StoreRegisterMetadataFormFragment() {

    }

    public static StoreRegisterMetadataFormFragment newInstance() {
        return new StoreRegisterMetadataFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_register_metadata_form, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.navigationStoreRegister));

        StoreRegisterViewModel viewModel = viewModelProvider.get(StoreRegisterViewModel.class);
        binding.setStoreRegisterViewModel(viewModel);

        Runnable onSuccess = this::navigateToNext;
        Runnable onError = this::onNetworkError;

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.storeRegisterMetadataRegister) {
                    viewModel.requestRegister(onSuccess, onError);
                }
                return true;
            }
        });

        binding.shopOpenDay.getEditText().setOnClickListener(new View.OnClickListener() {
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
                        String date = yearString + "-" + monthString + "-" + dayString;
                        binding.shopOpenDay.getEditText().setText(date);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePiceker.show();
            }
        });

        binding.shopAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> addressList;

                try{
                    addressList = geocoder.getFromLocationName(viewModel.getStreetAddress().get(), 1);
                    if(addressList.size() == 0) {
                        binding.shopAddress.setErrorEnabled(true);
                        binding.shopAddress.setError("주소를 확인할 수 없습니다. 올바른 주소인지 확인해주세요.");
                    }
                    else {
                        binding.shopAddressButton.setEnabled(false);
                        binding.shopAddressButton.setText("확인 완료");
                        binding.shopAddressButton.setTextColor(getResources().getColor(R.color.positive));
                        binding.shopAddress.setError(null);
                        binding.shopAddress.setErrorEnabled(false);
                        binding.shopAddress.setEnabled(false);
                        viewModel.setLng(String.valueOf(addressList.get(0).getLongitude()));
                        viewModel.setLat(String.valueOf(addressList.get(0).getLatitude()));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void navigateToNext() {
        NavHostFragment.findNavController(this).navigate(R.id.action_storeRegisterMetadataFormFragment_to_storeRegisteredFragment);
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
