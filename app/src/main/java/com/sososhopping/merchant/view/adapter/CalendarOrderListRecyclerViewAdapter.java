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
import com.sososhopping.merchant.databinding.ItemApprovedOrderListPickupBinding;
import com.sososhopping.merchant.databinding.ItemCancelOrderListDeliveryBinding;
import com.sososhopping.merchant.databinding.ItemCancelOrderListPickupBinding;
import com.sososhopping.merchant.databinding.ItemDoneOrderListDeliveryBinding;
import com.sososhopping.merchant.databinding.ItemDoneOrderListPickupBinding;
import com.sososhopping.merchant.databinding.ItemReadyOrderListDeliveryBinding;
import com.sososhopping.merchant.databinding.ItemReadyOrderListPickupBinding;
import com.sososhopping.merchant.databinding.ItemRejectOrderListDeliveryBinding;
import com.sososhopping.merchant.databinding.ItemRejectOrderListPickupBinding;
import com.sososhopping.merchant.model.order.dto.request.OrderProcessRequestDto;
import com.sososhopping.merchant.model.order.entity.Order;
import com.sososhopping.merchant.model.order.repository.OrderRepository;

import java.util.List;
import java.util.function.Consumer;

public class CalendarOrderListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Order> mValues;

    public static final int APPROVE_PICKUP = 0;
    public static final int READY_PICKUP = 1;
    public static final int REJECT_PICKUP = 2;
    public static final int CANCEL_PICKUP = 3;
    public static final int DONE_PICKUP = 4;
    public static final int APPROVE_DELIVERY = 5;
    public static final int READY_DELIVERY = 6;
    public static final int REJECT_DELIVERY = 7;
    public static final int CANCEL_DELIVERY = 8;
    public static final int DONE_DELIVERY = 9;

    public CalendarOrderListRecyclerViewAdapter(List<Order> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case APPROVE_PICKUP:{
                return new ApprovePickupViewHolder(ItemApprovedOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case READY_PICKUP:{
                return new ReadyPickupViewHolder(ItemReadyOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case REJECT_PICKUP:{
                return new RejectPickupViewHolder(ItemRejectOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case CANCEL_PICKUP:{
                return new CancelPickupViewHolder(ItemCancelOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case DONE_PICKUP:{
                return new DonePickupViewHolder(ItemDoneOrderListPickupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case APPROVE_DELIVERY:{
                return new ApproveDeliveryViewHolder(ItemApprovedOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case READY_DELIVERY:{
                return new ReadyDeliveryViewHolder(ItemReadyOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case REJECT_DELIVERY:{
                return new RejectDeliveryViewHolder(ItemRejectOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case CANCEL_DELIVERY:{
                return new CancelDeliveryViewHolder(ItemCancelOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
            case DONE_DELIVERY:{
                return new DoneDeliveryViewHolder(ItemDoneOrderListDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Consumer<Integer> onSuccessReady = this::onSuccessReady;
        Consumer<Integer> onSuccessReject = this::onSuccessReject;
        switch (getItemViewType(position)) {
            case APPROVE_PICKUP:{
                ApprovePickupViewHolder approveViewHolder = (ApprovePickupViewHolder) holder;
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
                break;
            }
            case READY_PICKUP:{
                ReadyPickupViewHolder readyViewHolder = (ReadyPickupViewHolder) holder;
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
                break;
            }
            case REJECT_PICKUP:{
                RejectPickupViewHolder rejectViewHolder = (RejectPickupViewHolder) holder;
                rejectViewHolder.mVisitTimeView.setText(mValues.get(position).getVisitDate());
                rejectViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
                rejectViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
                rejectViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rejectViewHolder.fold) {
                            rejectViewHolder.mDetails.setVisibility(View.VISIBLE);
                            rejectViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            rejectViewHolder.mDetails.setVisibility(View.GONE);
                            rejectViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        rejectViewHolder.fold = !rejectViewHolder.fold;
                    }
                });
                rejectViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                rejectViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                rejectViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                rejectViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                rejectViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                rejectViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());

                switch (mValues.get(position).getPaymentType()) {
                    case "CASH": {
                        rejectViewHolder.mPaymentTypeView.setText("매장에서 결제");
                        break;
                    }
                    case "CARD": {
                        rejectViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        rejectViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
            case CANCEL_PICKUP:{
                CancelPickupViewHolder cancelViewHolder = (CancelPickupViewHolder) holder;
                cancelViewHolder.mVisitTimeView.setText(mValues.get(position).getVisitDate());
                cancelViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
                cancelViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
                cancelViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelViewHolder.fold) {
                            cancelViewHolder.mDetails.setVisibility(View.VISIBLE);
                            cancelViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            cancelViewHolder.mDetails.setVisibility(View.GONE);
                            cancelViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        cancelViewHolder.fold = !cancelViewHolder.fold;
                    }
                });
                cancelViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                cancelViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                cancelViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                cancelViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                cancelViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                cancelViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());

                switch (mValues.get(position).getPaymentType()) {
                    case "CASH": {
                        cancelViewHolder.mPaymentTypeView.setText("매장에서 결제");
                        break;
                    }
                    case "CARD": {
                        cancelViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        cancelViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
            case DONE_PICKUP:{
                DonePickupViewHolder doneViewHolder = (DonePickupViewHolder) holder;
                doneViewHolder.mVisitTimeView.setText(mValues.get(position).getVisitDate());
                doneViewHolder.mVisitorNameView.setText(mValues.get(position).getOrdererName());
                doneViewHolder.mVisitorPhoneView.setText(mValues.get(position).getOrdererPhone());
                doneViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (doneViewHolder.fold) {
                            doneViewHolder.mDetails.setVisibility(View.VISIBLE);
                            doneViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            doneViewHolder.mDetails.setVisibility(View.GONE);
                            doneViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        doneViewHolder.fold = !doneViewHolder.fold;
                    }
                });
                doneViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                doneViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                doneViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                doneViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                doneViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                doneViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());

                switch (mValues.get(position).getPaymentType()) {
                    case "CASH": {
                        doneViewHolder.mPaymentTypeView.setText("매장에서 결제");
                        break;
                    }
                    case "CARD": {
                        doneViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        doneViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
            case APPROVE_DELIVERY:{
                ApproveDeliveryViewHolder approveViewHolder = (ApproveDeliveryViewHolder) holder;
                approveViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
                approveViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
                approveViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
                approveViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
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
                approveViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
                approveViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
                approveViewHolder.mSentButton.setOnClickListener(new View.OnClickListener() {
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
                break;
            }
            case READY_DELIVERY:{
                ReadyDeliveryViewHolder readyViewHolder = (ReadyDeliveryViewHolder) holder;
                readyViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
                readyViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
                readyViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
                readyViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
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
                readyViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
                readyViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
                switch (mValues.get(position).getPaymentType()) {
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
                break;
            }
            case REJECT_DELIVERY:{
                RejectDeliveryViewHolder rejectViewHolder = (RejectDeliveryViewHolder) holder;
                rejectViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
                rejectViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
                rejectViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
                rejectViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
                rejectViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rejectViewHolder.fold) {
                            rejectViewHolder.mDetails.setVisibility(View.VISIBLE);
                            rejectViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            rejectViewHolder.mDetails.setVisibility(View.GONE);
                            rejectViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        rejectViewHolder.fold = !rejectViewHolder.fold;
                    }
                });
                rejectViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                rejectViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                rejectViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                rejectViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                rejectViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                rejectViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
                rejectViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
                switch (mValues.get(position).getPaymentType()) {
                    case "CARD": {
                        rejectViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        rejectViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        rejectViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
            case CANCEL_DELIVERY:{
                CancelDeliveryViewHolder cancelViewHolder = (CancelDeliveryViewHolder) holder;
                cancelViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
                cancelViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
                cancelViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
                cancelViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
                cancelViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelViewHolder.fold) {
                            cancelViewHolder.mDetails.setVisibility(View.VISIBLE);
                            cancelViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            cancelViewHolder.mDetails.setVisibility(View.GONE);
                            cancelViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        cancelViewHolder.fold = !cancelViewHolder.fold;
                    }
                });
                cancelViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                cancelViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                cancelViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                cancelViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                cancelViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                cancelViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
                cancelViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
                switch (mValues.get(position).getPaymentType()) {
                    case "CARD": {
                        cancelViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        cancelViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        cancelViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
            case DONE_DELIVERY:{
                DoneDeliveryViewHolder doneViewHolder = (DoneDeliveryViewHolder) holder;
                doneViewHolder.mAddressView.setText(mValues.get(position).getDeliveryAddress());
                doneViewHolder.mAddressDetailView.setText(mValues.get(position).getDeliveryDetailedAddress());
                doneViewHolder.mNameView.setText(mValues.get(position).getOrdererName());
                doneViewHolder.mPhoneView.setText(mValues.get(position).getOrdererPhone());
                doneViewHolder.mToggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (doneViewHolder.fold) {
                            doneViewHolder.mDetails.setVisibility(View.VISIBLE);
                            doneViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_remove_24);
                        } else {
                            doneViewHolder.mDetails.setVisibility(View.GONE);
                            doneViewHolder.mToggleButton.setImageResource(R.drawable.ic_baseline_add_24);
                        }
                        doneViewHolder.fold = !doneViewHolder.fold;
                    }
                });
                doneViewHolder.mOrderListView.setAdapter(new OrderItemListRecyclerViewAdapter(mValues.get(position).getOrderItemList()));
                doneViewHolder.mTotalPriceView.setText(Integer.toString(mValues.get(position).getFinalPrice()));
                doneViewHolder.mItemPriceView.setText(Integer.toString(mValues.get(position).getOrderPrice()));
                doneViewHolder.mPointPriceView.setText(Integer.toString(mValues.get(position).getUsedPoint()));
                doneViewHolder.mCouponPriceView.setText(Integer.toString(mValues.get(position).getCouponDiscountPrice()));
                doneViewHolder.mDeliveryRateView.setText(Integer.toString(mValues.get(position).getDeliveryCharge()));
                doneViewHolder.mOrderDateView.setText(mValues.get(position).getCreatedAt());
                switch (mValues.get(position).getPaymentType()) {
                    case "CARD": {
                        doneViewHolder.mPaymentTypeView.setText("신용카드");
                        break;
                    }
                    case "LOCAL": {
                        doneViewHolder.mPaymentTypeView.setText("지역화폐");
                        break;
                    }
                    case "KAKAO": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(카카오페이)");
                        break;
                    }
                    case "NAVER": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(네이버페이)");
                        break;
                    }
                    case "PHONE": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(휴대폰)");
                        break;
                    }
                    case "TOSS": {
                        doneViewHolder.mPaymentTypeView.setText("간편결제(토스)");
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        int status = -1;
        if (mValues.get(position).getOrderType().equals("배송")){
            switch (mValues.get(position).getOrderStatus()) {
                case "APPROVE":{
                    status = APPROVE_DELIVERY;
                    break;
                }
                case "READY":{
                    status = READY_DELIVERY;
                    break;
                }
                case "REJECT":{
                    status = REJECT_DELIVERY;
                    break;
                }
                case "CANCEL":{
                    status = CANCEL_DELIVERY;
                    break;
                }
                case "DONE":{
                    status = DONE_DELIVERY;
                    break;
                }
            }
        } else {
            switch (mValues.get(position).getOrderStatus()) {
                case "APPROVE":{
                    status = APPROVE_PICKUP;
                    break;
                }
                case "READY":{
                    status = READY_PICKUP;
                    break;
                }
                case "REJECT":{
                    status = REJECT_PICKUP;
                    break;
                }
                case "CANCEL":{
                    status = CANCEL_PICKUP;
                    break;
                }
                case "DONE":{
                    status = DONE_PICKUP;
                    break;
                }
            }
        }
        return status;
    }

    public void onSuccessReady(int position) {
        mValues.get(position).setOrderStatus("READY");
        notifyItemChanged(position);
    }

    public void onSuccessReject(int position) {
        mValues.get(position).setOrderStatus("REJECT");
        notifyItemChanged(position);
    }

    public static class ApprovePickupViewHolder extends RecyclerView.ViewHolder {
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

        public ApprovePickupViewHolder(ItemApprovedOrderListPickupBinding binding) {
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

    public static class ReadyPickupViewHolder extends RecyclerView.ViewHolder {
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

        public ReadyPickupViewHolder(ItemReadyOrderListPickupBinding binding) {
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

    public static class RejectPickupViewHolder extends RecyclerView.ViewHolder {
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

        public RejectPickupViewHolder(ItemRejectOrderListPickupBinding binding) {
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

    public static class CancelPickupViewHolder extends RecyclerView.ViewHolder {
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

        public CancelPickupViewHolder(ItemCancelOrderListPickupBinding binding) {
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

    public static class DonePickupViewHolder extends RecyclerView.ViewHolder {
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

        public DonePickupViewHolder(ItemDoneOrderListPickupBinding binding) {
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

    public static class ApproveDeliveryViewHolder extends RecyclerView.ViewHolder {
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

        public ApproveDeliveryViewHolder(ItemApprovedOrderListDeliveryBinding binding) {
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

    public static class ReadyDeliveryViewHolder extends RecyclerView.ViewHolder {
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
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public ReadyDeliveryViewHolder(ItemReadyOrderListDeliveryBinding binding) {
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
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }

    public static class RejectDeliveryViewHolder extends RecyclerView.ViewHolder {
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
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public RejectDeliveryViewHolder(ItemRejectOrderListDeliveryBinding binding) {
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
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }

    public static class CancelDeliveryViewHolder extends RecyclerView.ViewHolder {
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
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public CancelDeliveryViewHolder(ItemCancelOrderListDeliveryBinding binding) {
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
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }

    public static class DoneDeliveryViewHolder extends RecyclerView.ViewHolder {
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
        public final Group mDetails;
        public final TextView mPaymentTypeView;
        public boolean fold = true;

        public DoneDeliveryViewHolder(ItemDoneOrderListDeliveryBinding binding) {
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
            mDetails = binding.details;
            mPaymentTypeView = binding.purchaseType;
        }
    }
}
