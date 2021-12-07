package com.sososhopping.merchant.model.review.dto.response;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.merchant.model.review.entity.Review;

import java.util.List;

public class ReviewListResponseDto {

    @SerializedName("averageScore")
    double averageScore;
    @SerializedName("size")
    int reviewCount;
    @SerializedName("reviews")
    List<Review> reviewList;

    public double getAverageScore() {
        return averageScore;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
