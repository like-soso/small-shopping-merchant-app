package com.sososhopping.merchant.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.FragmentBoardBinding;
import com.sososhopping.merchant.model.board.entity.Board;
import com.sososhopping.merchant.model.board.repository.BoardRepository;
import com.sososhopping.merchant.view.adapter.BoardListRecyclerViewAdapter;

import java.util.List;
import java.util.function.Consumer;

public class BoardFragment extends Fragment {

    private static final String STOREID = "storeId";

    private int storeId;

    FragmentBoardBinding binding;

    public BoardFragment() {

    }

    public static BoardFragment newInstance(int storeId) {
        BoardFragment fragment = new BoardFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false);

        Consumer<List<Board>> onItemListAcquired = this::onBoardListAcquired;
        Runnable onError = this::onNetworkError;

        BoardRepository.getInstance().requestBoardList(storeId, onItemListAcquired, onError);

        binding.shopListToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegister();
            }
        });

        return binding.getRoot();
    }

    private void navigateToRegister() {
        Bundle bundle = new Bundle();
        bundle.putInt(STOREID, storeId);
        NavHostFragment.findNavController(this).navigate(R.id.action_boardFragment_to_boardRegisterFragment, bundle);
    }

    private void onBoardListAcquired(List<Board> boardList) {
        binding.shopListRecyclerView.setAdapter(new BoardListRecyclerViewAdapter(boardList));
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }
}
