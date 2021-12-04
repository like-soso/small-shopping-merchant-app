package com.sososhopping.merchant.model.order.service;

import com.sososhopping.merchant.model.order.dto.request.OrderProcessRequestDto;
import com.sososhopping.merchant.model.order.dto.response.OrderListResponseDto;
import com.sososhopping.merchant.model.order.entity.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {

    @GET("store/{storeId}/orders")
    Call<OrderListResponseDto> requestOrderList(@Header("token") String token, @Path("storeId") int storeId, @Query("type") String type);

    @GET("store/{storeId}/orders")
    Call<OrderListResponseDto> requestOrderListOfDate(@Header("token") String token, @Path("storeId") int storeId, @Query("at") String date);

    @POST("store/{storeId}/orders/{orderId}")
    Call<Void> requestOrderProcessTo(@Header("token") String token, @Path("storeId") int storeId, @Path("orderId") int orderId, @Body OrderProcessRequestDto dto);
}
