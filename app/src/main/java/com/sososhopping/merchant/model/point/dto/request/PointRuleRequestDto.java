package com.sososhopping.merchant.model.point.dto.request;

import com.google.gson.annotations.SerializedName;

public class PointRuleRequestDto {

    @SerializedName("pointPolicyStatus")
    boolean pointPolicyStatus;
    @SerializedName("saveRate")
    double saveRate;

    public PointRuleRequestDto(boolean pointPolicyStatus, double saveRate) {
        this.pointPolicyStatus = pointPolicyStatus;
        this.saveRate = saveRate;
    }
}
