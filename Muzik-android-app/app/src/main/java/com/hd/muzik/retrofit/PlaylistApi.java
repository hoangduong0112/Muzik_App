package com.hd.muzik.retrofit;

import com.hd.muzik.model.Playlist;
import com.hd.muzik.model.PlaylistRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaylistApi {
    @GET("api/playlist/")
    Call<List<Playlist>> getPlaylist();

    @POST("api/playlist/")
    Call<Playlist> createNewPlaylist(@Body PlaylistRequest playlistRequest);

    @GET("api/playlist/{id}")
    Call<Playlist> getPlaylistById(@Path("id") int id);

    @PATCH("api/playlist/{id}")
    Call<Playlist> updatePlaylist(@Body PlaylistRequest playlistRequest, @Path("id") int id);

    @DELETE("api/playlist/{id}")
    Call<ResponseBody> deletePlaylist(@Path("id") int id);

    @PATCH("api/playlist/{playlistId}/song/{songId}")
    Call<Playlist>  addSongToPlaylist(@Path("playlistId") int playlistId, @Path("songId") int songId);

    @DELETE("api/playlist/{playlistId}/song/{songId}")
    Call<Playlist>  remmoveSongFromPlaylist(@Path("playlistId") int playlistId, @Path("songId") int songId);
}
