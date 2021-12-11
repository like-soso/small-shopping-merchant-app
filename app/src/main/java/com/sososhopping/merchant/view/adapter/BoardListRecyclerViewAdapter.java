package com.sososhopping.merchant.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemBoardListBinding;
import com.sososhopping.merchant.model.board.entity.Board;
import com.sososhopping.merchant.model.board.repository.BoardRepository;
import com.sososhopping.merchant.model.item.repository.ItemRepository;

import java.util.List;
import java.util.function.Consumer;

public class BoardListRecyclerViewAdapter extends RecyclerView.Adapter<BoardListRecyclerViewAdapter.ViewHolder>{

    private final List<Board> mValues;

    public BoardListRecyclerViewAdapter(List<Board> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBoardListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Consumer<Integer> onSuccess = this::onSuccess;
        holder.mItem = mValues.get(position);
        holder.mCategoryView.setText(holder.mItem.getWritingType().equals("EVENT") ? "이벤트" : "소식");
        holder.mTitleView.setText(holder.mItem.getTitle());
        holder.mDescriptionView.setText(holder.mItem.getContent());
        holder.mCreatedAt.setText(holder.mItem.getCreatedAt());
        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.mMore);
                popupMenu.inflate(R.menu.menu_board_popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.boardUpdate) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("storeId", mValues.get(position).getStoreId());
                            bundle.putInt("boardId", mValues.get(position).getId());
                            Navigation.findNavController((View) (holder.itemView.getParent().getParent().getParent().getParent())).navigate(R.id.action_boardFragment_to_boardUpdateFragment, bundle);
                        } else {
                            System.out.println(mValues.get(position).getId());
                            System.out.println(mValues.get(position).getStoreId());
                            BoardRepository.getInstance().requestBoardDelete(mValues.get(position).getStoreId(), mValues.get(position).getId(), position, onSuccess);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void onSuccess(int position) {
        mValues.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mCategoryView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final TextView mCreatedAt;
        public final ImageView mMore;
        public Board mItem;

        public ViewHolder(ItemBoardListBinding binding) {
            super(binding.getRoot());
            mCategoryView = binding.category;
            mTitleView = binding.Title;
            mDescriptionView = binding.description;
            mCreatedAt = binding.createdAt;
            mMore = binding.more;
        }
    }
}
