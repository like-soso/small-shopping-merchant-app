package com.sososhopping.merchant.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.databinding.ItemOrderItemListBinding;
import com.sososhopping.merchant.model.order.entity.OrderItem;

import java.util.List;

public class OrderItemListRecyclerViewAdapter extends RecyclerView.Adapter<OrderItemListRecyclerViewAdapter.ViewHolder> {

    private final List<OrderItem> mValues;

    public OrderItemListRecyclerViewAdapter(List<OrderItem> orderItemList) {
        this.mValues = orderItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mCountView.setText(Integer.toString(mValues.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mCountView;

        public ViewHolder(ItemOrderItemListBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mCountView = binding.count;
        }
    }
}
