package com.hd.muzik.retrofit;

import com.hd.muzik.model.LoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/api/users/signin")
    Call<ResponseBody> login(@Body LoginRequest loginRequest);
}
