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
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentBoardRegisterBinding;
import com.sososhopping.merchant.databinding.FragmentBoardUpdateBinding;
import com.sososhopping.merchant.model.board.entity.Board;
import com.sososhopping.merchant.model.board.repository.BoardRepository;
import com.sososhopping.merchant.model.item.entity.Item;
import com.sososhopping.merchant.viewmodel.BoardRegisterViewModel;
import com.sososhopping.merchant.viewmodel.BoardUpdateViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class BoardUpdateFragment extends Fragment {

    private static final String STOREID = "storeId";
    private static final String BOARDID = "boardId";

    private int storeId;
    private int boardId;

    FragmentBoardUpdateBinding binding;
    BoardUpdateViewModel viewModel;

    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    public BoardUpdateFragment() {

    }

    public static BoardRegisterFragment newInstance(int storeId, int boardId) {
        BoardRegisterFragment fragment = new BoardRegisterFragment();
        Bundle args = new Bundle();
        args.putInt(STOREID, storeId);
        args.putInt(BOARDID, boardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getInt(STOREID);
            boardId = getArguments().getInt(BOARDID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_update, container, false);

        viewModel = new ViewModelProvider(getViewModelStore(), new ViewModelProvider.NewInstanceFactory()).get(BoardUpdateViewModel.class);
        binding.setBoardUpdateViewModel(viewModel);

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

        Consumer<Board> onSuccessRead = this::onBoardAcquired;

        BoardRepository.getInstance().requestBoardItem(storeId, boardId, onSuccessRead);

        Runnable onSuccess = this::onSuccess;
        Runnable onError = this::onNetworkError;

        binding.shopListToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.boardRegister) {
                    viewModel.requestUpdate(storeId, boardId, onSuccess, onError);
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

    public void onBoardAcquired(Board board) {
        viewModel.setBoard(board);
        if (board.getImgUrl() != null && !board.getImgUrl().isEmpty()) {
            binding.mainImage.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(Uri.parse(board.getImgUrl()))
                    .into(binding.mainImage);
        }
    }

    public void onSuccess() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    public void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
