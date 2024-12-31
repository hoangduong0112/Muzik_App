package com.hd.muzik.retrofit;

import com.hd.muzik.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SongApi {
    @GET("api/song/")
    Call<List<Song>> getSongs();

    @GET("api/song/")
    Call<List<Song>> getSongsByKw(@Query("kw") String keyword);

    @GET("api/song/{id}")
    Call<Song> getSongById(@Path("id") int id);


//    like song
    @PUT("api/song/{id}/like")
    Call<Song> likeSong(@Path("id") int id);

    @GET("api/song/{id}/liked")
    Call<Boolean> checkLiked(@Path("id") int id);
}
