package com.hd.muzik.retrofit;

import com.hd.muzik.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SongApi {
    @GET("api/song/")
    Call<List<Song>> getSongs();
}
