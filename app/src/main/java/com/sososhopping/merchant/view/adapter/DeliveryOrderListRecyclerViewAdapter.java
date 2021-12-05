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

import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemApprovedOrderListDeliveryBinding;
import com.sososhopping.merchant.model.order.dto.request.OrderProcessRequestDto;
import com.sososhopping.merchant.model.order.entity.Order;
import com.sososhopping.merchant.model.order.repository.OrderRepository;

import java.util.List;
import java.util.function.Consumer;

public class DeliveryOrderListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> mValues;

    public DeliveryOrderListRecyclerViewAdapter(List<Order> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryViewHolder(ItemApprovedOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Consumer<Integer> onSuccess = this::onSuccess;
        PendingOrderListRecyclerViewAdapter.DeliveryViewHolder deliveryViewHolder = (PendingOrderListRecyclerViewAdapter.DeliveryViewHolder) holder;
        deliveryViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
        deliveryViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
        deliveryViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
        deliveryViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
        deliveryViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliveryViewHolder.fold) {
                    deliveryViewHolder.mDetails.setVisibility(View.VISIBLE);
                    deliveryViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                } else {
                    deliveryViewHolder.mDetails.setVisibility(View.GONE);
                    deliveryViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
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
                OrderRepository.getInstance().requestOrderProceed(
                        position,
                        mValues.get(position).getStoreId(),
                        mValues.get(position).getOrderId(),
                        OrderProcessRequestDto.of("READY"),
                        onSuccess
                );
            }
        });
        deliveryViewHolder.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRepository.getInstance().requestOrderProceed(
                        position,
                        mValues.get(position).getStoreId(),
                        mValues.get(position).getOrderId(),
                        OrderProcessRequestDto.of("REJECT"),
                        onSuccess
                );
            }
        });
        switch (mValues.get(position).getPaymentType()) {
            case "CARD": {
                deliveryViewHolder.mPaymentTypeView.setText("신용카드");
                break;
            }
            case "LOCAL": {
                deliveryViewHolder.mPaymentTypeView.setText("지역화폐");
                break;
            }
            case "KAKAO": {
                deliveryViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                break;
            }
            case "NAVER": {
                deliveryViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                break;
            }
            case "PHONE": {
                deliveryViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                break;
            }
            case "TOSS": {
                deliveryViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void onSuccess(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
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
        public final Button mSentButton;
        public final Button mCancelButton;
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public DeliveryViewHolder(ItemApprovedOrderListDeliveryBinding binding) {
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
            mSentButton = binding.sent;
            mCancelButton = binding.cancel;
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }
}
