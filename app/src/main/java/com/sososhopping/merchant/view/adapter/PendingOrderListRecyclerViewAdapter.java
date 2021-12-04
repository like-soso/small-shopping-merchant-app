package com.sososhopping.merchant.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.merchant.databinding.ItemPendingOrderListDeliveryBinding;
import com.sososhopping.merchant.databinding.ItemPendingOrderListPickupBinding;
import com.sososhopping.merchant.model.order.entity.Order;

import java.util.List;

public class PendingOrderListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Order> mValues;

    public static final int PICKUP = 0;
    public static final int DELIVERY = 1;

    public PendingOrderListRecyclerViewAdapter(List<Order> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PICKUP) return new PickupViewHolder(ItemPendingOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else return new DeliveryViewHolder(ItemPendingOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mValues.get(position).getOrderType().equals("배송")) {
            DeliveryViewHolder deliveryViewHolder = (DeliveryViewHolder) holder;
            deliveryViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
            deliveryViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
            deliveryViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
            deliveryViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
            deliveryViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deliveryViewHolder.fold) {
                        deliveryViewHolder.mDetails.setVisibility(View.VISIBLE);
                    } else {
                        deliveryViewHolder.mDetails.setVisibility(View.GONE);
                    }
                    deliveryViewHolder.fold = !deliveryViewHolder.fold;
                }
            });
            deliveryViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
            deliveryViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
            deliveryViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
            deliveryViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
            deliveryViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
            deliveryViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
            deliveryViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
            deliveryViewHolder.mConfirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ;
                }
            });
            deliveryViewHolder.mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ;
                }
            });
        } else {
            PickupViewHolder pickupViewHolder = (PickupViewHolder) holder;
            pickupViewHolder.mVisitTimeView.setText(mValues.get(position).getDeliveryAddress());
            pickupViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
            pickupViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
            pickupViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pickupViewHolder.fold) {
                        pickupViewHolder.mDetails.setVisibility(View.VISIBLE);
                    } else {
                        pickupViewHolder.mDetails.setVisibility(View.GONE);
                    }
                    pickupViewHolder.fold = !pickupViewHolder.fold;
                }
            });
            pickupViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
            pickupViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
            pickupViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
            pickupViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
            pickupViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
            pickupViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
            pickupViewHolder.mConfirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ;
                }
            });
            pickupViewHolder.mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ;
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
        return mValues.get(position).getOrderType().equals("배송") ? DELIVERY : PICKUP;
    }

    public static class PickupViewHolder extends RecyclerView.ViewHolder {
        public final TextView mVisitTimeView;
        public final TextView mVisitorNameView;
        public final TextView mVisitorPhoneView;
        public final ImageView mToggleButton;
        public final RecyclerView mOrderListView;
        public final TextView mTotalPriceView;
        public final TextView mItemPriceView;
        public final TextView mPointPriceView;
        public final TextView mCouponPriceView;
        public final TextView mOrderDateView;
        public final Button mConfirmButton;
        public final Button mCancelButton;
        public final Group mDetails;
        public boolean fold = true;

        public PickupViewHolder(ItemPendingOrderListPickupBinding binding) {
            super(binding.getRoot());
            mVisitTimeView = binding.visitTime;
            mVisitorNameView = binding.visitorName;
            mVisitorPhoneView = binding.visitorPhone;
            mToggleButton = binding.toggleButton;
            mOrderListView = binding.orderList;
            mTotalPriceView = binding.totalPrice;
            mItemPriceView = binding.itemPrice;
            mPointPriceView = binding.pointPrice;
            mCouponPriceView = binding.couponPrice;
            mOrderDateView = binding.orderDate;
            mConfirmButton = binding.confirm;
            mCancelButton = binding.cancel;
            mDetails = binding.details;
        }
    }

    public static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAddressView;
        public final TextView mAddressDetailView;
        public final TextView mNameView;
        public final TextView mPhoneView;
        public final ImageView mToggleButton;
        public final RecyclerView mOrderListView;
        public final TextView mTotalPriceView;
        public final TextView mItemPriceView;
        public final TextView mPointPriceView;
        public final TextView mCouponPriceView;
        public final TextView mDeliveryRateView;
        public final TextView mOrderDateView;
        public final Button mConfirmButton;
        public final Button mCancelButton;
        public final Group mDetails;
        public boolean fold = true;

        public DeliveryViewHolder(ItemPendingOrderListDeliveryBinding binding) {
            super(binding.getRoot());
            mAddressView = binding.deliveryAddress;
            mAddressDetailView = binding.deliveryAddressDetail;
            mNameView = binding.deliveryName;
            mPhoneView = binding.deliveryPhone;
            mToggleButton = binding.toggleButton;
            mOrderListView = binding.orderList;
            mTotalPriceView = binding.totalPrice;
            mItemPriceView = binding.itemPrice;
            mPointPriceView = binding.pointPrice;
            mCouponPriceView = binding.couponPrice;
            mDeliveryRateView = binding.deliveryPrice;
            mOrderDateView = binding.orderDate;
            mConfirmButton = binding.confirm;
            mCancelButton = binding.cancel;
            mDetails = binding.details;
        }
    }
}
