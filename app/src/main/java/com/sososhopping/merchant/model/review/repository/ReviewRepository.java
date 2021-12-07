package com.sososhopping.merchant.model.review.repository;

import com.sososhopping.merchant.model.review.dto.response.ReviewListResponseDto;
import com.sososhopping.merchant.model.review.service.ReviewService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {
    private static ReviewRepository instance;
    private final ReviewService service;

    private ReviewRepository() {
        service = ApiServiceFactory.createService(ReviewService.class);
    }

    public static synchronized ReviewRepository getInstance() {
        if (instance == null) {
            instance = new ReviewRepository();
        }

        return instance;
    }

    public void requestReviewList(int storeId, Consumer<ReviewListResponseDto> onSuccess, Runnable onError) {
        service.requestReviewList(TokenStore.getAuthToken(), storeId).enqueue(new Callback<ReviewListResponseDto>() {
            @Override
            public void onResponse(Call<ReviewListResponseDto> call, Response<ReviewListResponseDto> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    System.out.println(response.code());
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<ReviewListResponseDto> call, Throwable t) {
                onError.run();
            }
        });
    }
}
