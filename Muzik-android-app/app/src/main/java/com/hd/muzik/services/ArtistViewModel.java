package com.hd.muzik.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Album;
import com.hd.muzik.model.Artist;
import com.hd.muzik.retrofit.AlbumApi;
import com.hd.muzik.retrofit.ArtistApi;
import com.hd.muzik.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistViewModel extends ViewModel {
    private final MutableLiveData<List<Artist>> artists = new MutableLiveData<>();

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }

    public void fetchArtist(){
        ArtistApi artistApi = RetrofitInstance.getInstance().create(ArtistApi.class);
        artistApi.getArtists().enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    artists.postValue(response.body());
                    Log.d("success", "success");
                }
                else
                    Log.d("fail", "fail");
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                artists.postValue(null);
                Log.d("fail2", "fail2");
            }

        });
    }

    public void fetchArtistByKw(String kw){
        ArtistApi artistApi = RetrofitInstance.getInstance().create(ArtistApi.class);
        artistApi.getArtistsByKw(kw).enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    artists.postValue(response.body());
                    Log.d("success", "success");
                }
                else
                    Log.d("fail", "fail");
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                artists.postValue(null);
                Log.d("fail2", "fail2");
            }

        });
    }

}
