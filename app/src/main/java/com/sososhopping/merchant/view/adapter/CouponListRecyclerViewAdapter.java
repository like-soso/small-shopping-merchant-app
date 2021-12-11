package com.sososhopping.merchant.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.HeaderCouponListBinding;
import com.sososhopping.merchant.databinding.ItemCouponListBinding;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateBeingRequestDto;
import com.sososhopping.merchant.model.coupon.entity.Coupon;
import com.sososhopping.merchant.model.coupon.repository.CouponRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CouponListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0;
    public static final int DATA = 1;

    List<Item> mValues;

    public CouponListRecyclerViewAdapter(List<Item> items) {
        this.mValues = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER: {
                System.out.println("HEADER");
                return new HeaderViewHolder(HeaderCouponListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case DATA: {
                return new DataViewHolder(ItemCouponListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mValues.get(position) instanceof HeaderItem) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.item = (HeaderItem) mValues.get(position);
            headerViewHolder.mHeaderText.setText(headerViewHolder.item.header);
            headerViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (headerViewHolder.item.dataItems == null) {
                        headerViewHolder.item.dataItems = new ArrayList<>();
                        int count = 0;
                        int pos = mValues.indexOf(headerViewHolder.item);
                        System.out.println(headerViewHolder.item.dataItems.size());
                        while (mValues.size() > pos + 1 && mValues.get(pos + 1) instanceof DataItem) {
                            headerViewHolder.item.dataItems.add((DataItem) mValues.remove(pos + 1));
                            count++;
                        }
                        notifyDataSetChanged();
                        headerViewHolder.mButton.setImageResource(R.drawable.ic_baseline_add_24);
                    } else {
                        int pos = mValues.indexOf(headerViewHolder.item);
                        int index = pos + 1;
                        for (Item i : headerViewHolder.item.dataItems) {
                            mValues.add(index, i);
                            index++;
                        }
                        notifyDataSetChanged();
                        headerViewHolder.mButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        headerViewHolder.item.dataItems = null;
                    }
                }
            });
        } else {
            Consumer<Integer> onSuccess = this::onSuccess;
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.item = (DataItem) mValues.get(position);
            dataViewHolder.mCode.setText(dataViewHolder.item.couponCode);
            dataViewHolder.mName.setText(dataViewHolder.item.couponName);
            dataViewHolder.mAmount.setText(dataViewHolder.item.couponType.equals("RATE") ? Double.toString(dataViewHolder.item.rateAmount) : Integer.toString(dataViewHolder.item.fixAmount));
            dataViewHolder.mType.setText(dataViewHolder.item.couponType.equals("RATE") ? "%" : "원");
            dataViewHolder.mMinOrder.setText(Integer.toString(dataViewHolder.item.minimumOrderPrice));
            dataViewHolder.mExpiry.setText(dataViewHolder.item.expiryDate);
            dataViewHolder.mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), dataViewHolder.mMore);
                    if(dataViewHolder.item.isBeing) {
                        popupMenu.inflate(R.menu.menu_coupon_popup_being);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId() == R.id.couponBeingUpdate) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("storeId", ((DataItem) mValues.get(position)).storeId);
                                    bundle.putInt("couponId", ((DataItem) mValues.get(position)).id);
                                    Navigation.findNavController((View) (holder.itemView.getParent().getParent().getParent().getParent())).navigate(R.id.action_couponFragment_to_couponUpdateBeingFragment, bundle);
                                } else {
                                    CouponRepository.getInstance().requestCouponSuspendBeing(((DataItem) mValues.get(position)).storeId, ((DataItem) mValues.get(position)).id, CouponUpdateBeingRequestDto.toSuspend(), position, onSuccess);
                                }
                                return true;
                            }
                        });
                    } else {
                        popupMenu.inflate(R.menu.menu_coupon_popup_expected);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if(item.getItemId() == R.id.couponExpectedDelete) {
                                    CouponRepository.getInstance().requestCouponDelete(((DataItem) mValues.get(position)).storeId, ((DataItem) mValues.get(position)).id, position, onSuccess);
                                }
                                return true;
                            }
                        });
                    }
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
        return mValues.get(position) instanceof HeaderItem ? HEADER : DATA;
    }

    public void onSuccess(int position) {
        mValues.remove(position);
        notifyDataSetChanged();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderItem item;
        public ImageButton mButton;
        public TextView mHeaderText;

        public HeaderViewHolder(HeaderCouponListBinding binding) {
            super(binding.getRoot());
            this.mHeaderText = binding.header;
            mButton = binding.button;
        }
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        public DataItem item;
        public TextView mCode;
        public TextView mName;
        public TextView mAmount;
        public TextView mType;
        public TextView mMinOrder;
        public TextView mExpiry;
        public ImageView mMore;

        public DataViewHolder(ItemCouponListBinding binding) {
            super(binding.getRoot());
            mCode = binding.code;
            mName = binding.Title;
            mAmount = binding.discountAmount;
            mType = binding.type;
            mMinOrder = binding.minOrder;
            mExpiry = binding.expiryDate;
            mMore = binding.more;
        }
    }

    public static class Item {

    }

    public static class HeaderItem extends Item {
        String header;
        List<DataItem> dataItems;

        public HeaderItem(String header) {
            this.header = header;
            dataItems = new ArrayList<>();
        }

        public void addDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }

        public List<DataItem> getDataItems() {
            return dataItems;
        }
    }

    public static class DataItem extends Item {
        int id;
        int storeId;
        boolean isBeing;
        String storeName;
        String couponName;
        int stockQuantity;
        String couponCode;
        int minimumOrderPrice;
        String issuedStartDate;
        String issuedDueDate;
        String expiryDate;
        String couponType;
        double rateAmount;
        int fixAmount;

        public DataItem(Coupon couponList, boolean isBeing) {
            this.id = couponList.getId();
            this.storeId = couponList.getStoreId();
            this.storeName = couponList.getStoreName();
            this.couponName = couponList.getCouponName();
            this.stockQuantity = couponList.getStockQuantity();
            this.couponCode = couponList.getCouponCode();
            this.minimumOrderPrice = couponList.getMinimumOrderPrice();
            this.issuedStartDate = couponList.getIssuedStartDate();
            this.issuedDueDate = couponList.getIssuedDueDate();
            this.expiryDate = couponList.getExpiryDate();
            this.couponType = couponList.getCouponType();
            this.rateAmount = couponList.getRateAmount();
            this.fixAmount = couponList.getFixAmount();
            this.isBeing = isBeing;
        }
    }
}
