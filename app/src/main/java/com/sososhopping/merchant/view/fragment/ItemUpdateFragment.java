package com.sososhopping.merchant.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.bumptech.glide.Glide;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentItemUpdateBinding;
import com.sososhopping.merchant.model.item.entity.Item;
import com.sososhopping.merchant.model.item.repository.ItemRepository;
import com.sososhopping.merchant.viewmodel.ItemUpdateViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class ItemUpdateFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String ITEMID = "itemId";

    private int storeId;
    private int itemId;

    FragmentItemUpdateBinding binding;

    ItemUpdateViewModel viewModel;

    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    public ItemUpdateFragment() {

    }

    public static ItemUpdateFragment newInstance(int storeId, int itemId) {
        ItemUpdateFragment fragment = new ItemUpdateFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putInt(ITEMID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            itemId = getArguments().getInt(ITEMID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_update, container, false);

        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(ItemUpdateViewModel.class);
        binding.setItemUpdateViewModel(viewModel);

        Consumer<Item> onItemAcquired = this::onItemAcquired;

        ItemRepository.getInstance().requestItem(storeId, itemId, onItemAcquired);

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

        binding.selectMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbumForResult();
            }
        });

        Runnable onSuccess = this::onSuccess;
        Runnable onError = this::onNetworkError;

        Runnable onNameEmpty = this::onNameEmpty;
        Runnable onNameNotEmpty = this::onNameNotEmpty;
        Runnable onUnitEmpty = this::onUnitEmpty;
        Runnable onUnitNotEmpty = this::onUnitNotEmpty;
        Runnable onPriceEmpty = this::onPriceEmpty;
        Runnable onPriceNotEmpty = this::onPriceNotEmpty;
        Runnable onPriceInvalid = this::onPriceInvalid;

        binding.shopListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemUpdateToolbar){
                    if (viewModel.valid(
                            onNameEmpty,
                            onNameNotEmpty,
                            onUnitEmpty,
                            onUnitNotEmpty,
                            onPriceEmpty,
                            onPriceNotEmpty,
                            onPriceInvalid
                    )) {
                        viewModel.requestUpdate(storeId, itemId, onSuccess, onError);
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

        return binding.getRoot();
    }

    public void onNameEmpty() {
        binding.signupFormEmailLayout.setErrorEnabled(true);
        binding.signupFormEmailLayout.setError("필수 입력 항목입니다.");
    }

    public void onNameNotEmpty() {
        binding.signupFormEmailLayout.setErrorEnabled(false);
        binding.signupFormEmailLayout.setError(null);
    }

    public void onUnitEmpty() {
        binding.itemUnitLayout.setErrorEnabled(true);
        binding.itemUnitLayout.setError("필수 입력 항목입니다.");
    }

    public void onUnitNotEmpty() {
        binding.itemUnitLayout.setErrorEnabled(false);
        binding.itemUnitLayout.setError(null);
    }

    public void onPriceEmpty() {
        binding.itemUnitPriceLayout.setErrorEnabled(true);
        binding.itemUnitPriceLayout.setError("필수 입력 항목입니다.");
    }

    public void onPriceInvalid() {
        binding.itemUnitPriceLayout.setErrorEnabled(true);
        binding.itemUnitPriceLayout.setError("양수만 입력 가능");
    }

    public void onPriceNotEmpty() {
        binding.itemUnitLayout.setErrorEnabled(false);
        binding.itemUnitLayout.setError(null);
    }

    private void openAlbumForResult() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imageActivityResultLauncher.launch(intent);
    }

    public void onItemAcquired(Item itemList) {
        viewModel.setItem(itemList);
        Glide.with(getContext())
                .load(Uri.parse(viewModel.getImgUrl()))
                .into(binding.mainImage);
    }

    private void onSuccess() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
