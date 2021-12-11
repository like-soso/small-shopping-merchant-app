package com.sososhopping.merchant.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentBoardRegisterBinding;
import com.sososhopping.merchant.viewmodel.BoardRegisterViewModel;

import java.io.IOException;
import java.io.InputStream;

public class BoardRegisterFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentBoardRegisterBinding binding;

    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    public BoardRegisterFragment() {

    }

    public static BoardRegisterFragment newInstance(int storeId) {
        BoardRegisterFragment fragment = new BoardRegisterFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_register, container, false);

        BoardRegisterViewModel viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(BoardRegisterViewModel.class);
        binding.setBoardRegisterViewModel(viewModel);

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
                                binding.mainImage.setVisibility(View.VISIBLE);
                                viewModel.setBitmap(bitmap);
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

        binding.selectMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbumForResult();
            }
        });

        Runnable onSuccess = this::onSuccess;
        Runnable onError = this::onNetworkError;

        binding.shopListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.boardRegister) {
                    viewModel.requestRegister(storeId, onSuccess, onError);
                }
                return true;
            }
        });

        binding.type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.event) {
                    viewModel.setCategory("EVENT");
                } else {
                    viewModel.setCategory("PROMOTION");
                }
            }
        });

        return binding.getRoot();
    }

    private void openAlbumForResult() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imageActivityResultLauncher.launch(intent);
    }

    private void onSuccess() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
