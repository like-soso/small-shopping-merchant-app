package com.sososhopping.merchant.model.order.repository;

import com.sososhopping.merchant.model.order.dto.request.OrderProcessRequestDto;
import com.sososhopping.merchant.model.order.dto.response.OrderListResponseDto;
import com.sososhopping.merchant.model.order.entity.Order;
import com.sososhopping.merchant.model.order.service.OrderService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private static OrderRepository instance;
    private final OrderService service;

    private OrderRepository() {
        service = ApiServiceFactory.createService(OrderService.class);
    }

    public static synchronized OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public void requestOrderList(int storeId, String type, Consumer<OrderListResponseDto> onSuccess, Runnable onError) {
        service.requestOrderList(TokenStore.getAuthToken(), storeId, type).enqueue(new Callback<OrderListResponseDto>() {
            @Override
            public void onResponse(Call<OrderListResponseDto> call, Response<OrderListResponseDto> response) {
                System.out.println(response.body());
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<OrderListResponseDto> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });
    }

    public void requestOrderListAt(int storeId, String date) {
        service.requestOrderListOfDate(TokenStore.getAuthToken(), storeId, date).enqueue(new Callback<OrderListResponseDto>() {
            @Override
            public void onResponse(Call<OrderListResponseDto> call, Response<OrderListResponseDto> response) {
                ;
            }

            @Override
            public void onFailure(Call<OrderListResponseDto> call, Throwable t) {
                ;
            }
        });
    }

    public void requestOrderProceed(int position, int storeId, int orderId, OrderProcessRequestDto dto, Consumer<Integer> onSuccess) {
        service.requestOrderProcessTo(TokenStore.getAuthToken(), storeId, orderId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.accept(position);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ;
            }
        });
    }
}
