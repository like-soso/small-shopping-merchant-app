package com.sososhopping.merchant.model.board.repository;

import android.graphics.Bitmap;

import com.sososhopping.merchant.model.board.dto.request.BoardRegisterRequestDto;
import com.sososhopping.merchant.model.board.entity.Board;
import com.sososhopping.merchant.model.board.service.BoardService;
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

public class BoardRepository {

    private static BoardRepository instance;
    private final BoardService service;

    private BoardRepository() {
        this.service = ApiServiceFactory.createService(BoardService.class);
    }

    public static synchronized BoardRepository getInstance() {
        if(instance == null) {
            instance = new BoardRepository();
        }

        return instance;
    }

    public void requestBoardList(int storeId, Consumer<List<Board>> consumer, Runnable onError) {
        service.requestBoardList(TokenStore.getAuthToken(), storeId).enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.code() == 200) {
                    consumer.accept(response.body());
                } else {
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestBoardRegister(int storeId,
                                     BoardRegisterRequestDto dto,
                                     Bitmap bitmap,
                                     Runnable onSuccess,
                                     Runnable onError){
        service.requestBoardRegister(TokenStore.getAuthToken(), storeId, MultipartBody.Part.createFormData("dto", "dto", new DtoJsonRequestBody<>(dto)), MultipartBody.Part.createFormData("img", "image.jpg", new BitmapRequestBody(bitmap))).enqueue(new Callback<Void>() {
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

    public void requestBoardDelete(int storeId, int boardId, int position, Consumer<Integer> onSuccess) {
        service.requestBoardDelete(TokenStore.getAuthToken(), storeId, boardId).enqueue(new Callback<Void>() {
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
