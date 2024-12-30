package com.hd.muzik.retrofit;

import android.content.Context;

import com.hd.muzik.utils.TokenManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofitWithAuth;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getInstanceWithAuth(Context context) {
        if (retrofitWithAuth == null) {
            TokenManager tokenManager = new TokenManager(context);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(tokenManager)) // Thêm AuthInterceptor
                    .build();

            retrofitWithAuth = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitWithAuth;
    }

}