package com.sososhopping.merchant.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStoreListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(
                                (View) (v.getParent().getParent().getParent().getParent().getParent().getParent())
                        ).navigate(
                                R.id.action_mainFragment_to_pendingStoreFragment
                        );
                    }
                });

                break;
            }
            case "REJECT": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_remove_circle_outline_24)
                        .into(holder.mVerified);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(
                                (View) (v.getParent().getParent().getParent().getParent().getParent().getParent())
                        ).navigate(
                                R.id.action_mainFragment_to_rejectedStoreFragment
                        );
                    }
                });

                break;
            }
            case "SUSPEND": {
                Glide.with(holder.itemView.getContext())
                        .load(R.drawable.ic_baseline_not_interested_24)
                        .into(holder.mVerified);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(
                                (View) (v.getParent().getParent().getParent().getParent().getParent().getParent())
                        ).navigate(
                                R.id.action_mainFragment_to_deniedStoreFragment
                        );
                    }
                });

                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final ImageView mImage;
        public final ImageView mVerified;

        public ViewHolder(ItemStoreListBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.title;
            mDescriptionView = binding.description;
            mImage = binding.image;
            mVerified = binding.verifiedStatus;
        }
    }
}