package com.hd.muzik.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Album;
import com.hd.muzik.retrofit.AlbumApi;
import com.hd.muzik.retrofit.RetrofitInstance;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumViewModel extends ViewModel {
    private final MutableLiveData<List<Album>> albums = new MutableLiveData<>();

    public LiveData<List<Album>> getAlbums() {
        return albums;
    }

    public void fetchAlbums() {
        AlbumApi albumApi = RetrofitInstance.getInstance().create(AlbumApi.class);
        albumApi.getAlbum().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    albums.postValue(response.body());
                    Log.d("success", "success");
                }
                else
                    Log.d("fail", "fail");
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                albums.postValue(null);
                Log.d("fail2", "fail2");
            }
        });
    }
}
