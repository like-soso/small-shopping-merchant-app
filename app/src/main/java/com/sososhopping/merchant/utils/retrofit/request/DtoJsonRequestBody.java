package com.sososhopping.merchant.utils.retrofit.request;

import androidx.annotation.Nullable;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class DtoJsonRequestBody<T> extends RequestBody {
    T data;

    public DtoJsonRequestBody(T dto){
        this.data = dto;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.writeUtf8(new GsonBuilder().create().toJson(data));
    }
}
