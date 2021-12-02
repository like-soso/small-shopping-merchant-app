package com.sososhopping.merchant.utils.retrofit.factory;

import com.sososhopping.merchant.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {

    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T createService(Class<T> serviceClass){
        return retrofit.create(serviceClass);
    }
}
