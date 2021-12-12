package com.sososhopping.merchant.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.DialogPasswordChangedBinding;
import com.sososhopping.merchant.databinding.DialogProfileUpdatedBinding;

public class ProfileUpdatedDialog extends DialogFragment {

    DialogProfileUpdatedBinding binding;

    public ProfileUpdatedDialog() {

    }

    public static ProfileUpdatedDialog newInstance() {
        return new ProfileUpdatedDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_profile_updated, container, false);

        binding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        android.app.Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void closeDialog() {
        this.dismiss();
    }
}
