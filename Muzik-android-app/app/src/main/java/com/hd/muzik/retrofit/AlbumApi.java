package com.hd.muzik.retrofit;

import com.hd.muzik.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumApi {
    @GET("/api/album/")
    Call<List<Album>> getAlbum();
}
