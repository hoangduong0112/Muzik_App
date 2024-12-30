package com.hd.muzik.retrofit;

import com.hd.muzik.utils.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException, IOException {
        String token = tokenManager.getToken();
        if (token != null && !token.isEmpty()) {
            // Thêm Bearer token vào Authorization header
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(chain.request());
    }
}

