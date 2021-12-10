package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentPointBinding;
import com.sososhopping.merchant.model.point.dto.response.PointRuleResponseDto;
import com.sososhopping.merchant.model.point.repository.PointRepository;
import com.sososhopping.merchant.utils.token.TokenStore;
import com.sososhopping.merchant.viewmodel.PointRuleViewModel;

import java.util.function.Consumer;

public class PointFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentPointBinding binding;
    PointRuleViewModel viewModel;

    public PointFragment() {

    }

    public static PointFragment newInstance(int storeId) {
        PointFragment fragment = new PointFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_point, container, false);
        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(PointRuleViewModel.class);
        binding.setPointManageViewModel(viewModel);

        Consumer<PointRuleResponseDto> onPointRuleChecked = this::onPointRuleChecked;
        Runnable onError = this::onNetworkError;
        PointRepository.getInstance().requestPointRule(storeId, onPointRuleChecked, onError);

        Runnable onPointRuleChanged = this::onRuleUpdated;
        Runnable onValid = this::onValid;
        Runnable onInvalid = this::onInvalid;

        binding.pointRuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.validate(onValid, onInvalid)){
                    viewModel.requestUpdate(storeId, onPointRuleChanged, onError);
                }
            }
        });

        binding.enablePoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setUsePoint(isChecked);
                binding.signupFormEmailLayout.setEnabled(isChecked);
            }
        });

        binding.pointHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(STOREID, storeId);
                Navigation.findNavController(v).navigate(R.id.action_pointFragment_to_pointCheckDialog, bundle);
            }
        });

        return binding.getRoot();
    }

    public void onPointRuleChecked(PointRuleResponseDto dto) {
        binding.enablePoint.setChecked(dto.isPointPolicyStatus());
        viewModel.setUsePoint(dto.isPointPolicyStatus());
        binding.signupFormEmailLayout.setEnabled(dto.isPointPolicyStatus());
        binding.signupFormEmailLayout.getEditText().setText(Double.toString(dto.getSaveRate()));
    }

    public void onRuleUpdated() {
        NavHostFragment.findNavController(this).navigate(R.id.action_pointFragment_to_pointRuleChangedDialog);
    }

    public void onValid() {
        binding.signupFormEmailLayout.setErrorEnabled(false);
        binding.signupFormEmailLayout.setError(null);
    }

    public void onInvalid() {
        binding.signupFormEmailLayout.setErrorEnabled(true);
        binding.signupFormEmailLayout.setError("포인트 적립 비율은 양수만 사용할 수 있습니다.");
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
