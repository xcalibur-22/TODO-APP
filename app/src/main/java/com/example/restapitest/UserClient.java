package com.example.restapitest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {


    @POST("auth/login/")
    Call<Token> getToken(@Body Login login);

    @GET("auth/profile/")
    Call<User> getProfile(@Header("Authorization") String token);

    @POST("auth/register/")
    Call<Token> registerProfile(@Body RegisterModel registerModel);

    @GET("todo/")
    Call<List<TodoModel>> getTodoList(@Header("Authorization") String token);

    @POST("todo/create/")
    Call<ResponseBody> createTodo(@Header("Authorization") String token,
                                  @Body TodoModel todomodel);

    @PATCH("todo/{id}/")
    Call<TodoModel> updateTodo(@Path("id") int postId,
                               @Header("Authorization") String token,
                               @Body TodoModel todomodel);

    @DELETE("todo/{id}/")
    Call<ResponseBody> deleteTodo(@Path("id") int postId,
                                  @Header("Authorization") String token);





}
