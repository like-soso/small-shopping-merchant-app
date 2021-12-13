package com.sososhopping.merchant.model.item.repository;

import android.graphics.Bitmap;

import com.sososhopping.merchant.model.item.dto.request.ItemRegisterRequestDto;
import com.sososhopping.merchant.model.item.dto.request.ItemUpdateRequestDto;
import com.sososhopping.merchant.model.item.entity.Item;
import com.sososhopping.merchant.model.item.service.ItemService;
import com.sososhopping.merchant.utils.retrofit.factory.ApiServiceFactory;
import com.sososhopping.merchant.utils.retrofit.request.BitmapRequestBody;
import com.sososhopping.merchant.utils.retrofit.request.DtoJsonRequestBody;
import com.sososhopping.merchant.utils.token.TokenStore;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {
    private static ItemRepository instance;
    private final ItemService service;

    private ItemRepository() {
        this.service = ApiServiceFactory.createService(ItemService.class);
    }

    public static synchronized ItemRepository getInstance() {
        if(instance == null) {
            instance = new ItemRepository();
        }

        return instance;
    }

    public void requestItemList(int storeId, Consumer<List<Item>> onSuccess, Runnable onError) {
        service.requestStoreItemList(TokenStore.getAuthToken(), storeId).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestRegisterItem(int storeId, ItemRegisterRequestDto dto, Bitmap bitmap, Runnable onSuccess, Runnable onError){
        service.requestRegisterItem(TokenStore.getAuthToken(), storeId, MultipartBody.Part.createFormData("dto", "dto", new DtoJsonRequestBody<>(dto)), MultipartBody.Part.createFormData("img", "image.jpg", new BitmapRequestBody(bitmap))).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    onSuccess.run();
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestItem(int storeId, int itemId, Consumer<Item> onSuccess, Runnable onError) {
        service.requestStoreItem(TokenStore.getAuthToken(), storeId, itemId).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body());
                }
                else onError.run();
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestItemDelete(int storeId, int itemId, int position, Consumer<Integer> onSuccess, Runnable onInvalid, Runnable onError) {
        service.requestItemDelete(TokenStore.getAuthToken(), storeId, itemId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.accept(position);
                else if (response.code() == 403) onInvalid.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestItemUpdate(int storeId, int itemId, ItemUpdateRequestDto dto, Bitmap bitmap, Runnable onSuccess, Runnable onError) {
        service.requestItemUpdate(TokenStore.getAuthToken(), storeId, itemId, MultipartBody.Part.createFormData("dto", "dto", new DtoJsonRequestBody<>(dto)), MultipartBody.Part.createFormData("img", "image.jpg", new BitmapRequestBody(bitmap))).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) onSuccess.run();
                else onError.run();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }
}
