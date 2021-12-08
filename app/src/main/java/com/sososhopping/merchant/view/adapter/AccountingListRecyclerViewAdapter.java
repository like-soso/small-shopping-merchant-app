package com.sososhopping.merchant.view.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemAccountingListNegativeBinding;
import com.sososhopping.merchant.databinding.ItemAccountingListPositiveBinding;
import com.sososhopping.merchant.model.accounting.entity.Accounting;

import java.util.List;

public class AccountingListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int POSITIVE = 0;
    private static final int NEGATIVE = 1;

    private final List<Accounting> mValues;

    public AccountingListRecyclerViewAdapter(List<Accounting> items) {
        mValues = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == POSITIVE) return new PositiveViewHolder(ItemAccountingListPositiveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return new NegativeViewHolder(ItemAccountingListNegativeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mValues.get(position).getAmount() >= 0) {
            PositiveViewHolder positiveViewHolder = (PositiveViewHolder) holder;
            positiveViewHolder.mAmount.setText(Integer.toString(mValues.get(position).getAmount()));
            positiveViewHolder.mTitleView.setText(mValues.get(position).getMemo());
            positiveViewHolder.mCreatedAt.setText(mValues.get(position).getDateTime());
            positiveViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), positiveViewHolder.mMore);
                    popupMenu.inflate(R.menu.menu_accounting_popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.reviewChat) {
                                System.out.println(mValues.get(position).getStoreId());
                                System.out.println(mValues.get(position).getId());
                            } else {
                                System.out.println(mValues.get(position).getId());
                                System.out.println(mValues.get(position).getStoreId());
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        } else {
            NegativeViewHolder negativeViewHolder = (NegativeViewHolder) holder;
            negativeViewHolder.mAmount.setText(Integer.toString(mValues.get(position).getAmount()));
            negativeViewHolder.mTitleView.setText(mValues.get(position).getMemo());
            negativeViewHolder.mCreatedAt.setText(mValues.get(position).getDateTime());
            negativeViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), negativeViewHolder.mMore);
                    popupMenu.inflate(R.menu.menu_accounting_popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.reviewChat) {
                                System.out.println(mValues.get(position).getStoreId());
                                System.out.println(mValues.get(position).getId());
                            } else {
                                System.out.println(mValues.get(position).getId());
                                System.out.println(mValues.get(position).getStoreId());
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position).getAmount() < 0) return NEGATIVE;
        return POSITIVE;
    }

    public static class PositiveViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAmount;
        public final TextView mTitleView;
        public final TextView mCreatedAt;
        public final ImageView mMore;

        public PositiveViewHolder(ItemAccountingListPositiveBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.Title;
            mCreatedAt = binding.createdAt;
            mAmount = binding.amount;
            mMore = binding.more;
        }
    }

    public static class NegativeViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAmount;
        public final TextView mTitleView;
        public final TextView mCreatedAt;
        public final ImageView mMore;

        public NegativeViewHolder(ItemAccountingListNegativeBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.Title;
            mCreatedAt = binding.createdAt;
            mAmount = binding.amount;
            mMore = binding.more;
        }
    }
}