package com.hd.muzik.retrofit;

import com.hd.muzik.model.Artist;
import com.hd.muzik.model.Song;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistApi {
    @GET("api/artist/")
    Call<List<Artist>> getArtists();

    @GET("api/artist/")
    Call<List<Artist>> getArtistsByKw(@Query("kw") String keyword);

    @GET("api/artist/{id}")
    Call<Song> getSongById(@Path("id") int id);

}
