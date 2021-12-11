package com.sososhopping.merchant.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemReviewListBinding;
import com.sososhopping.merchant.model.review.entity.Review;
import com.sososhopping.merchant.view.activity.MainActivity;

import java.util.List;

public class ReviewListRecyclerViewAdapter extends RecyclerView.Adapter<ReviewListRecyclerViewAdapter.ViewHolder>{

    private final String mStoreName;

    private final List<Review> mValues;

    Context context;

    public ReviewListRecyclerViewAdapter(String mStoreName, List<Review> mValues, Context context) {
        this.mStoreName = mStoreName;
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemReviewListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mUsernameView.setText(mValues.get(position).getUsername());
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.mCreatedAt.setText(mValues.get(position).getCreatedAt());
        holder.mRatingBar.setRating((float) (mValues.get(position).getScore()));
        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.mMore);
                popupMenu.inflate(R.menu.menu_review_popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.reviewChat) {
                            String storeId = Integer.toString(mValues.get(position).getStoreId());
                            String userId = Integer.toString(mValues.get(position).getUserId());
                            String userName = mValues.get(position).getUsername();
                            String chatroomId = ((MainActivity) context).makeChatroom(storeId, userId, userName, mStoreName);

                            Bundle bundle = new Bundle();
                            bundle.putString("chatroomId", chatroomId);
                            bundle.putString("userName", userName);

                            Navigation.findNavController((View) (v.getParent().getParent().getParent().getParent()))
                                    .navigate(R.id.action_reviewListFragment_to_conversationFragment, bundle);
                            //TODO: 여기 조치
                            System.out.println(mStoreName);
                            System.out.println("CHAT");
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt("storeId", mValues.get(position).getStoreId());
                            bundle.putInt("customerId", mValues.get(position).getUserId());
                            bundle.putString("customerName", mValues.get(position).getUsername());
                            Navigation.findNavController((View) (holder.itemView.getParent().getParent().getParent().getParent())).navigate(R.id.action_reviewListFragment_to_reportFragment, bundle);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mUsernameView;
        public final TextView mContentView;
        public final TextView mCreatedAt;
        public final RatingBar mRatingBar;
        public final ImageView mMore;

        public ViewHolder(ItemReviewListBinding binding) {
            super(binding.getRoot());
            mUsernameView = binding.userName;
            mContentView = binding.content;
            mCreatedAt = binding.datetime;
            mRatingBar = binding.rating;
            mMore = binding.more;
        }
    }
}
