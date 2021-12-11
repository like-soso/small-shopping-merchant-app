package com.sososhopping.merchant.model.board.service;

import com.sososhopping.merchant.model.board.entity.Board;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BoardService {
    @GET("store/{storeId}/writing")
    Call<List<Board>> requestBoardList(@Header("token") String token, @Path("storeId") int storeId);

    @Multipart
    @POST("store/{storeId}/writing")
    Call<Void> requestBoardRegister(@Header("token") String token, @Path("storeId") int storeId, @Part MultipartBody.Part dto, @Part MultipartBody.Part image);

    @DELETE("store/{storeId}/writing/{writingId}")
    Call<Void> requestBoardDelete(@Header("token") String token, @Path("storeId") int storeId, @Path("writingId") int boardId);

    @GET("store/{storeId}/writing/{writingId}")
    Call<Board> requestBoardItem(@Header("token") String token, @Path("storeId") int storeId, @Path("writingId") int boardId);

    @Multipart
    @PATCH("store/{storeId}/writing/{writingId}")
    Call<Void> requestBoardUpdate(@Header("token") String token, @Path("storeId") int storeId, @Path("writingId") int boardId, @Part MultipartBody.Part dto, @Part MultipartBody.Part image);
}
