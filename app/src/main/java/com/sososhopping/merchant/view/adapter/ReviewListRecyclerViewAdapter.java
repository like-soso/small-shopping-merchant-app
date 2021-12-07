package com.sososhopping.merchant.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemReviewListBinding;
import com.sososhopping.merchant.model.review.entity.Review;

import java.util.List;

public class ReviewListRecyclerViewAdapter extends RecyclerView.Adapter<ReviewListRecyclerViewAdapter.ViewHolder>{

    private final String mStoreName;

    private final List<Review> mValues;

    public ReviewListRecyclerViewAdapter(String mStoreName, List<Review> mValues) {
        this.mStoreName = mStoreName;
        this.mValues = mValues;
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
                            Bundle bundle = new Bundle();
                            bundle.putInt("CUSTOMERID", mValues.get(position).getUserId());
                            bundle.putString("CUSTOMERNAME", mValues.get(position).getUsername());
                            bundle.putString("STORENAME", mStoreName);
                            //TODO: 여기 조치
                            System.out.println(mStoreName);
                            System.out.println("CHAT");
                        } else {
                            System.out.println("REPORT");
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
