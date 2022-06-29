package com.example.restapitest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {


    @POST("auth/login/")
    Call<Token> getToken(@Body Login login);

    @GET("auth/profile/")
    Call<User> getProfile(@Header("Authorization") Token token);


}
