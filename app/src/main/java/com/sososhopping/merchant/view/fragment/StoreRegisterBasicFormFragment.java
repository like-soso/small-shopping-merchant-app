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

        Runnable onNameEmpty = this::onNameEmpty;
        Runnable onNameNotEmpty = this::onNameNotEmpty;
        Runnable onPhoneEmpty = this::onPhoneEmpty;
        Runnable onPhoneNotEmpty = this::onPhoneNotEmpty;
        Runnable onCategoryEmpty = this::onCategoryNotSelected;
        Runnable onCategoryNotEmpty = this::onCategorySelected;
        Runnable onMondayConsistent = this::onMondayConsistent;
        Runnable onMondayInconsistent = this::onMondayInConsistent;
        Runnable onTuesdayConsistent = this::onTuesdayConsistent;
        Runnable onTuesdayInconsistent = this::onTuesdayInConsistent;
        Runnable onWednesdayConsistent = this::onWednesdayConsistent;
        Runnable onWednesdayInconsistent = this::onWednesdayInConsistent;
        Runnable onThursdayConsistent = this::onThursdayConsistent;
        Runnable onThursdayInconsistent = this::onThursdayInConsistent;
        Runnable onFridayConsistent = this::onFridayConsistent;
        Runnable onFridayInconsistent = this::onFridayInConsistent;
        Runnable onSaturdayConsistent = this::onSaturdayConsistent;
        Runnable onSaturdayInconsistent = this::onSaturdayInConsistent;
        Runnable onSundayConsistent = this::onSundayConsistent;
        Runnable onSundayInconsistent = this::onSundayInConsistent;
        Runnable onDeliveryConsistent = this::onDeliveryConsistent;
        Runnable onDeliveryInconsistent = this::onDeliveryInconsistent;

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.storeRegisterBasicNext) {
                    if (viewModel.validateBasicForm(
                            onNameEmpty,
                            onNameNotEmpty,
                            onPhoneEmpty,
                            onPhoneNotEmpty,
                            onCategoryEmpty,
                            onCategoryNotEmpty,
                            onMondayConsistent,
                            onMondayInconsistent,
                            onTuesdayConsistent,
                            onTuesdayInconsistent,
                            onWednesdayConsistent,
                            onWednesdayInconsistent,
                            onThursdayConsistent,
                            onThursdayInconsistent,
                            onFridayConsistent,
                            onFridayInconsistent,
                            onSaturdayConsistent,
                            onSaturdayInconsistent,
                            onSundayConsistent,
                            onSundayInconsistent,
                            onDeliveryConsistent,
                            onDeliveryInconsistent
                    )) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_storeRegisterBasicFormFragment_to_storeRegisterMetadataFormFragment);
                    }
                }

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
                    binding.openHourMonday.getEditText().setText("");
                    binding.closeHourMonday.setEnabled(false);
                    binding.closeHourMonday.getEditText().setText("");
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
                    binding.openHourTuesday.getEditText().setText("");
                    binding.closeHourTuesday.setEnabled(false);
                    binding.closeHourTuesday.getEditText().setText("");
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
                    binding.openHourWednesday.getEditText().setText("");
                    binding.closeHourWednesday.setEnabled(false);
                    binding.closeHourWednesday.getEditText().setText("");
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
                    binding.openHourThursday.getEditText().setText("");
                    binding.closeHourThursday.setEnabled(false);
                    binding.closeHourThursday.getEditText().setText("");
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
                    binding.openHourFriday.getEditText().setText("");
                    binding.closeHourFriday.setEnabled(false);
                    binding.closeHourFriday.getEditText().setText("");
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
                    binding.openHourSaturday.getEditText().setText("");
                    binding.closeHourSaturday.setEnabled(false);
                    binding.closeHourSaturday.getEditText().setText("");
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
                    binding.openHourSunday.getEditText().setText("");
                    binding.closeHourSunday.setEnabled(false);
                    binding.closeHourSunday.getEditText().setText("");
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
                    binding.deliveryFee.getEditText().setText("");
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

        System.out.println(viewModel.getCategory().get());

        return binding.getRoot();
    }

    public void onNameEmpty() {
        binding.name.setErrorEnabled(true);
        binding.name.setError("필수 입력 항목입니다.");
    }

    public void onNameNotEmpty() {
        binding.name.setErrorEnabled(false);
        binding.name.setError(null);
    }

    public void onPhoneEmpty() {
        binding.phone.setErrorEnabled(true);
        binding.phone.setError("필수 입력 항목입니다.");
    }

    public void onPhoneNotEmpty() {
        binding.name.setErrorEnabled(false);
        binding.name.setError(null);
    }

    public void onCategoryNotSelected() {
        binding.categoryError.setVisibility(View.VISIBLE);
    }

    public void onCategorySelected() {
        binding.categoryError.setVisibility(View.GONE);
    }

    public void onMondayInConsistent() {
        binding.openHourMonday.setErrorEnabled(true);
        binding.closeHourMonday.setErrorEnabled(true);
        binding.openHourMonday.setError("필수 입력 사항");
        binding.closeHourMonday.setError("필수 입력 사항");
    }

    public void onMondayConsistent() {
        binding.openHourMonday.setErrorEnabled(false);
        binding.closeHourMonday.setErrorEnabled(false);
        binding.openHourMonday.setError(null);
        binding.closeHourMonday.setError(null);
    }

    public void onTuesdayInConsistent() {
        binding.openHourTuesday.setErrorEnabled(true);
        binding.closeHourTuesday.setErrorEnabled(true);
        binding.openHourTuesday.setError("필수 입력 사항");
        binding.closeHourTuesday.setError("필수 입력 사항");
    }

    public void onTuesdayConsistent() {
        binding.openHourTuesday.setErrorEnabled(false);
        binding.closeHourTuesday.setErrorEnabled(false);
        binding.openHourTuesday.setError(null);
        binding.closeHourTuesday.setError(null);
    }

    public void onWednesdayInConsistent() {
        binding.openHourWednesday.setErrorEnabled(true);
        binding.closeHourWednesday.setErrorEnabled(true);
        binding.openHourWednesday.setError("필수 입력 사항");
        binding.closeHourWednesday.setError("필수 입력 사항");
    }

    public void onWednesdayConsistent() {
        binding.openHourWednesday.setErrorEnabled(false);
        binding.closeHourWednesday.setErrorEnabled(false);
        binding.openHourWednesday.setError(null);
        binding.closeHourWednesday.setError(null);
    }

    public void onThursdayInConsistent() {
        binding.openHourThursday.setErrorEnabled(true);
        binding.closeHourThursday.setErrorEnabled(true);
        binding.openHourThursday.setError("필수 입력 사항");
        binding.closeHourThursday.setError("필수 입력 사항");
    }

    public void onThursdayConsistent() {
        binding.openHourThursday.setErrorEnabled(false);
        binding.closeHourThursday.setErrorEnabled(false);
        binding.openHourThursday.setError(null);
        binding.closeHourThursday.setError(null);
    }

    public void onFridayInConsistent() {
        binding.openHourFriday.setErrorEnabled(true);
        binding.closeHourFriday.setErrorEnabled(true);
        binding.openHourFriday.setError("필수 입력 사항");
        binding.closeHourFriday.setError("필수 입력 사항");
    }

    public void onFridayConsistent() {
        binding.openHourFriday.setErrorEnabled(false);
        binding.closeHourFriday.setErrorEnabled(false);
        binding.openHourFriday.setError(null);
        binding.closeHourFriday.setError(null);
    }

    public void onSaturdayInConsistent() {
        binding.openHourSaturday.setErrorEnabled(true);
        binding.closeHourSaturday.setErrorEnabled(true);
        binding.openHourSaturday.setError("필수 입력 사항");
        binding.closeHourSaturday.setError("필수 입력 사항");
    }

    public void onSaturdayConsistent() {
        binding.openHourSunday.setErrorEnabled(false);
        binding.closeHourSunday.setErrorEnabled(false);
        binding.openHourSunday.setError(null);
        binding.closeHourSunday.setError(null);
    }

    public void onSundayInConsistent() {
        binding.openHourSunday.setErrorEnabled(true);
        binding.closeHourSunday.setErrorEnabled(true);
        binding.openHourSunday.setError("필수 입력 사항");
        binding.closeHourSunday.setError("필수 입력 사항");
    }

    public void onSundayConsistent() {
        binding.openHourSunday.setErrorEnabled(false);
        binding.closeHourSunday.setErrorEnabled(false);
        binding.openHourSunday.setError(null);
        binding.closeHourSunday.setError(null);
    }

    public void onDeliveryInconsistent() {
        binding.deliveryFee.setErrorEnabled(true);
        binding.deliveryFee.setError("배송 제공 시 필수 입력");
    }

    public void onDeliveryConsistent() {
        binding.deliveryFee.setErrorEnabled(false);
        binding.deliveryFee.setError(null);
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