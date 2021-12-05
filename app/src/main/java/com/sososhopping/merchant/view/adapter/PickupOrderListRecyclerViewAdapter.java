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
import com.sososhopping.merchant.databinding.ItemApprovedOrderListPickupBinding;
import com.sososhopping.merchant.databinding.ItemReadyOrderListPickupBinding;
import com.sososhopping.merchant.model.order.dto.request.OrderProcessRequestDto;
import com.sososhopping.merchant.model.order.entity.Order;
import com.sososhopping.merchant.model.order.repository.OrderRepository;

import java.util.List;
import java.util.function.Consumer;

public class PickupOrderListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> mValues;

    public static final int APPROVE = 0;
    public static final int READY = 1;

    public PickupOrderListRecyclerViewAdapter(List<Order> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == APPROVE) return new ApproveViewHolder(ItemApprovedOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        else return new ReadyViewHolder(ItemReadyOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mValues.get(position).getOrderStatus().equals("APPROVE")) {
            Consumer<Integer> onSuccessReady = this::onSuccessReady;
            Consumer<Integer> onSuccessReject = this::onSuccessReject;
            ApproveViewHolder approveViewHolder = (ApproveViewHolder) holder;
            approveViewHolder.mVisitTimeView.setText(mValues.get(position).getVisitDate());
            approveViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
            approveViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
            approveViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (approveViewHolder.fold) {
                        approveViewHolder.mDetails.setVisibility(View.VISIBLE);
                        approveViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                    } else {
                        approveViewHolder.mDetails.setVisibility(View.GONE);
                        approveViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                    }
                    approveViewHolder.fold = !approveViewHolder.fold;
                }
            });
            approveViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
            approveViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
            approveViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
            approveViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
            approveViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
            approveViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
            approveViewHolder.mReadyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderRepository.getInstance().requestOrderProceed(
                            position,
                            mValues.get(position).getStoreId(),
                            mValues.get(position).getOrderId(),
                            OrderProcessRequestDto.of("READY"),
                            onSuccessReady
                    );
                }
            });
            approveViewHolder.mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderRepository.getInstance().requestOrderProceed(
                            position,
                            mValues.get(position).getStoreId(),
                            mValues.get(position).getOrderId(),
                            OrderProcessRequestDto.of("REJECT"),
                            onSuccessReject
                    );
                }
            });

            switch (mValues.get(position).getPaymentType()) {
                case "CASH": {
                    approveViewHolder.mPaymentTypeView.setText("매장에서 결제");
                    break;
                }
                case "CARD": {
                    approveViewHolder.mPaymentTypeView.setText("신용카드");
                    break;
                }
                case "LOCAL": {
                    approveViewHolder.mPaymentTypeView.setText("지역화폐");
                    break;
                }
                case "KAKAO": {
                    approveViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                    break;
                }
                case "NAVER": {
                    approveViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                    break;
                }
                case "PHONE": {
                    approveViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                    break;
                }
                case "TOSS": {
                    approveViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                    break;
                }
            }
        } else {
            ReadyViewHolder readyViewHolder = (ReadyViewHolder) holder;
            readyViewHolder.mVisitTimeView.setText(mValues.get(position).getVisitDate());
            readyViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
            readyViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
            readyViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (readyViewHolder.fold) {
                        readyViewHolder.mDetails.setVisibility(View.VISIBLE);
                        readyViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                    } else {
                        readyViewHolder.mDetails.setVisibility(View.GONE);
                        readyViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                    }
                    readyViewHolder.fold = !readyViewHolder.fold;
                }
            });
            readyViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
            readyViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
            readyViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
            readyViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
            readyViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
            readyViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());

            switch (mValues.get(position).getPaymentType()) {
                case "CASH": {
                    readyViewHolder.mPaymentTypeView.setText("매장에서 결제");
                    break;
                }
                case "CARD": {
                    readyViewHolder.mPaymentTypeView.setText("신용카드");
                    break;
                }
                case "LOCAL": {
                    readyViewHolder.mPaymentTypeView.setText("지역화폐");
                    break;
                }
                case "KAKAO": {
                    readyViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                    break;
                }
                case "NAVER": {
                    readyViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                    break;
                }
                case "PHONE": {
                    readyViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                    break;
                }
                case "TOSS": {
                    readyViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getOrderStatus().equals("APPROVE") ? APPROVE : READY;
    }

    public void onSuccessReady(int position) {
        mValues.get(position).setOrderStatus("READY");
        notifyItemChanged(position);
    }

    public void onSuccessReject(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public static class ApproveViewHolder extends RecyclerView.ViewHolder {
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
        public final Button mReadyButton;
        public final Button mCancelButton;
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public ApproveViewHolder(ItemApprovedOrderListPickupBinding binding) {
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
            mReadyButton = binding.ready;
            mCancelButton = binding.cancel;
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }

    public static class ReadyViewHolder extends RecyclerView.ViewHolder {
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
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public ReadyViewHolder(ItemReadyOrderListPickupBinding binding) {
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
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }
}
