package com.sososhopping.merchant.view.fragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentStoreRegisterBasicFormBinding;
import com.sososhopping.merchant.viewmodel.StoreRegisterViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class StoreRegisterBasicFormFragment extends Fragment {

    FragmentStoreRegisterBasicFormBinding binding;
    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    public StoreRegisterBasicFormFragment() {

    }

    public static StoreRegisterBasicFormFragment newInstance() {
        return new StoreRegisterBasicFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_register_basic_form, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(R.id.navigationStoreRegister));

        StoreRegisterViewModel viewModel = viewModelProvider.get(StoreRegisterViewModel.class);
        binding.setStoreRegisterViewModel(viewModel);

        viewModel.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_store));

        imageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            try{
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                binding.mainImage.setImageBitmap(bitmap);
                                viewModel.setBitmap(bitmap);
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.selectMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbumForResult();
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.storeRegisterBasicNext) Navigation.findNavController(binding.getRoot()).navigate(R.id.action_storeRegisterBasicFormFragment_to_storeRegisterMetadataFormFragment);

                return true;
            }
        });

        binding.category.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.store_register_basic_spinner)){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                if(position == 0) {
                    TextView textview = (TextView) v;
                    ((TextView) v).setTextColor(getResources().getColor(R.color.gray));
                } else {
                    TextView textview = (TextView) v;
                    ((TextView) v).setTextColor(getResources().getColor(R.color.black));
                }

                return v;
            }
        });

        binding.category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCategory((String)parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.checkBoxMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenMonday(false);
                    binding.openHourMonday.setEnabled(false);
                    binding.openHourMonday.getEditText().setText(null);
                    binding.closeHourMonday.setEnabled(false);
                    binding.closeHourMonday.getEditText().setText(null);
                    binding.openHourMonday.getEditText().setClickable(false);
                    binding.closeHourMonday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenMonday(true);
                    binding.openHourMonday.setEnabled(true);
                    binding.closeHourMonday.setEnabled(true);
                    binding.openHourMonday.getEditText().setClickable(true);
                    binding.closeHourMonday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenTuesday(false);
                    binding.openHourTuesday.setEnabled(false);
                    binding.openHourTuesday.getEditText().setText(null);
                    binding.closeHourTuesday.setEnabled(false);
                    binding.closeHourTuesday.getEditText().setText(null);
                    binding.openHourTuesday.getEditText().setClickable(false);
                    binding.closeHourTuesday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenTuesday(true);
                    binding.openHourTuesday.setEnabled(true);
                    binding.closeHourTuesday.setEnabled(true);
                    binding.openHourTuesday.getEditText().setClickable(true);
                    binding.closeHourTuesday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenWednesday(false);
                    binding.openHourWednesday.setEnabled(false);
                    binding.openHourWednesday.getEditText().setText(null);
                    binding.closeHourWednesday.setEnabled(false);
                    binding.closeHourWednesday.getEditText().setText(null);
                    binding.openHourWednesday.getEditText().setClickable(false);
                    binding.closeHourWednesday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenWednesday(true);
                    binding.openHourWednesday.setEnabled(true);
                    binding.closeHourWednesday.setEnabled(true);
                    binding.openHourWednesday.getEditText().setClickable(true);
                    binding.closeHourWednesday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenThursday(false);
                    binding.openHourThursday.setEnabled(false);
                    binding.openHourThursday.getEditText().setText(null);
                    binding.closeHourThursday.setEnabled(false);
                    binding.closeHourThursday.getEditText().setText(null);
                    binding.openHourThursday.getEditText().setClickable(false);
                    binding.closeHourThursday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenThursday(true);
                    binding.openHourThursday.setEnabled(true);
                    binding.closeHourThursday.setEnabled(true);
                    binding.openHourThursday.getEditText().setClickable(true);
                    binding.closeHourThursday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenFriday(false);
                    binding.openHourFriday.setEnabled(false);
                    binding.openHourFriday.getEditText().setText(null);
                    binding.closeHourFriday.setEnabled(false);
                    binding.closeHourFriday.getEditText().setText(null);
                    binding.openHourFriday.getEditText().setClickable(false);
                    binding.closeHourFriday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenFriday(true);
                    binding.openHourFriday.setEnabled(true);
                    binding.closeHourFriday.setEnabled(true);
                    binding.openHourFriday.getEditText().setClickable(true);
                    binding.closeHourFriday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenSaturday(false);
                    binding.openHourSaturday.setEnabled(false);
                    binding.openHourSaturday.getEditText().setText(null);
                    binding.closeHourSaturday.setEnabled(false);
                    binding.closeHourSaturday.getEditText().setText(null);
                    binding.openHourSaturday.getEditText().setClickable(false);
                    binding.closeHourSaturday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenSaturday(true);
                    binding.openHourSaturday.setEnabled(true);
                    binding.closeHourSaturday.setEnabled(true);
                    binding.openHourSaturday.getEditText().setClickable(true);
                    binding.closeHourSaturday.getEditText().setClickable(true);
                }
            }
        });

        binding.checkBoxSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    viewModel.setOpenSunday(false);
                    binding.openHourSunday.setEnabled(false);
                    binding.openHourSunday.getEditText().setText(null);
                    binding.closeHourSunday.setEnabled(false);
                    binding.closeHourSunday.getEditText().setText(null);
                    binding.openHourSunday.getEditText().setClickable(false);
                    binding.closeHourSunday.getEditText().setClickable(false);
                } else {
                    viewModel.setOpenSunday(true);
                    binding.openHourSunday.setEnabled(true);
                    binding.closeHourSunday.setEnabled(true);
                    binding.openHourSunday.getEditText().setClickable(true);
                    binding.closeHourSunday.getEditText().setClickable(true);
                }
            }
        });

        binding.delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setDelivery(isChecked);
                if (isChecked) {
                    binding.deliveryFee.setEnabled(true);
                } else {
                    binding.deliveryFee.setEnabled(false);
                    binding.deliveryFee.getEditText().setText(null);
                }
            }
        });

        binding.openHourMonday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourMonday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourMonday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourMonday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourTuesday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourTuesday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourTuesday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourTuesday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourWednesday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourWednesday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourWednesday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourWednesday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourThursday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourThursday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourThursday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourThursday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourFriday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourFriday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourFriday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourFriday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourSaturday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourSaturday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourSaturday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourSaturday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.openHourSunday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.openHourSunday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        binding.closeHourSunday.getEditText().setOnClickListener(new View.OnClickListener() {
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

                        String time = hourString + minuteString;
                        binding.closeHourSunday.getEditText().setText(time);
                    }
                }, hour, minute, false);
                mTimePicker.show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openAlbumForResult() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imageActivityResultLauncher.launch(intent);
    }
}