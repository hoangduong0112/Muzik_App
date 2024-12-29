package com.hd.muzik.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_TOKEN = "jwt_token";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }


    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public boolean hasToken() {
        return sharedPreferences.contains(KEY_TOKEN);
    }

    public void clearToken() {
        editor.remove(KEY_TOKEN);
        editor.apply();
    }


    public boolean isTokenValid() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }
}
