package com.sososhopping.merchant.model.point.dto.response;

import com.google.gson.annotations.SerializedName;

public class PointRuleResponseDto {

    @SerializedName("pointPolicyStatus")
    boolean pointPolicyStatus;
    @SerializedName("saveRate")
    double saveRate;

    public boolean isPointPolicyStatus() {
        return pointPolicyStatus;
    }

    public double getSaveRate() {
        return saveRate;
    }
}
