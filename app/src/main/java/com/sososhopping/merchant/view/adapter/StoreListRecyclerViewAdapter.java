package com.sososhopping.merchant.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemStoreListBinding;
import com.sososhopping.merchant.model.store.entity.StoreBrief;

import java.util.List;

public class StoreListRecyclerViewAdapter extends RecyclerView.Adapter<StoreListRecyclerViewAdapter.ViewHolder>{

    private final List<StoreBrief> mValues;

    public StoreListRecyclerViewAdapter(List<StoreBrief> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStoreListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        if (mValues.get(position).getImgUrl() != null){
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(mValues.get(position).getImgUrl()))
                    .into(holder.mImage);
        }
        switch (mValues.get(position).getStoreStatus()){
            case "ACTIVE": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_check_circle_24)
                        .into(holder.mVerified);
                break;
            }
            case "PENDING": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_hourglass_empty_24)
                        .into(holder.mVerified);
                break;
            }
            case "REJECT": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_remove_circle_outline_24)
                        .into(holder.mVerified);
                break;
            }
            case "SUSPEND": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_not_interested_24)
                        .into(holder.mVerified);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final ImageView mImage;
        public final ImageView mVerified;
        public StoreBrief mItem;

        public ViewHolder(ItemStoreListBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.shopListItemTitle;
            mDescriptionView = binding.shopListItemDescription;
            mImage = binding.shopListItemImage;
            mVerified = binding.shopListItemVerified;
        }
    }
}