package com.sososhopping.merchant.model.coupon.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sososhopping.merchant.model.coupon.dto.request.CouponModifyRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponRegisterRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateBeingRequestDto;
import com.sososhopping.merchant.model.coupon.dto.request.CouponUpdateExpectedRequestDto;
import com.sososhopping.merchant.model.coupon.dto.response.CouponCheckResponseDto;
import com.sososhopping.merchant.model.coupon.dto.response.CouponListResponseDto;
import com.sososhopping.merchant.model.coupon.entity.Coupon;
import com.sososhopping.merchant.model.coupon.service.CouponService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponRepository {

    private static CouponRepository instance;
    private final CouponService service;

    private CouponRepository() {
        this.service = ApiServiceFactory.createService(CouponService.class);
    }

    public synchronized static CouponRepository getInstance() {
        if (instance == null) {
            instance = new CouponRepository();
        }
        return instance;
    }

    public void requestCouponList(int storeId, Consumer<CouponListResponseDto> onSuccess, Runnable onError) {
        service.requestCouponList(TokenStore.getAuthToken(), storeId).enqueue(new Callback<CouponListResponseDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CouponListResponseDto> call, Response<CouponListResponseDto> response) {
                System.out.println(response.code());
                if(response.code() == 200) onSuccess.accept(response.body());
                else onError.run();
            }

            @Override
            public void onFailure(Call<CouponListResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestCoupon(int storeId, int couponId, Consumer<Coupon> onSuccess) {
        service.requestCoupon(TokenStore.getAuthToken(), storeId, couponId).enqueue(new Callback<Coupon>() {
            @Override
            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                if (response.code() == 200) onSuccess.accept(response.body());
            }

            @Override
            public void onFailure(Call<Coupon> call, Throwable t) {

            }
        });
    }

    public void requestRegister(int storeId, CouponRegisterRequestDto dto) {
        service.requestCouponRegister(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ;
            }
        });
    }

    public void requestCouponUpdateBeing(int storeId, int couponId, CouponUpdateBeingRequestDto dto, Runnable onSuccess) {
        service.requestCouponUpdateBeing(TokenStore.getAuthToken(), storeId, couponId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                if (response.code() == 200) onSuccess.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestCouponUpdateExpected(int storeId, int couponId, CouponUpdateExpectedRequestDto dto, Runnable onSuccess) {
        System.out.println("called");
        service.requestCouponUpdateExpected(TokenStore.getAuthToken(), storeId, couponId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                if (response.code() == 200) onSuccess.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestCouponCheck(int storeId, String userPhone, String couponCode, Runnable onSuccess){
        service.requestCouponCheck(TokenStore.getAuthToken(), storeId, userPhone, couponCode).enqueue(new Callback<CouponCheckResponseDto>() {
            @Override
            public void onResponse(Call<CouponCheckResponseDto> call, Response<CouponCheckResponseDto> response) {
                System.out.println(call.request().url().toString());
                if (response.code() == 200) onSuccess.run();
            }

            @Override
            public void onFailure(Call<CouponCheckResponseDto> call, Throwable t) {
                ;
            }
        });
    }

    public void requestCouponModify(int storeId, CouponModifyRequestDto dto) {
        service.requestCouponModify(TokenStore.getAuthToken(), storeId, dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ;
            }
        });
    }

    public void requestCouponDelete(int storeId, int couponId, int position, Consumer<Integer> onSuccess) {
        service.requestCouponDelete(TokenStore.getAuthToken(), storeId, couponId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.accept(position);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
