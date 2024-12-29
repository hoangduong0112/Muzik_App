package com.hd.muzik.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Song;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.retrofit.SongApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongViewModel extends ViewModel {
    private final MutableLiveData<List<Song>> songs = new MutableLiveData<List<Song>>();

    public LiveData<List<Song>> getSongs() {
        return songs;
    }
    public void fetchSongs() {
        SongApi songApi = RetrofitInstance.getInstance().create(SongApi.class);
        songApi.getSongs().enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    songs.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                songs.postValue(null);
            }
        });
    }

    public void fetchSongsByKw(String kw) {
        SongApi songApi = RetrofitInstance.getInstance().create(SongApi.class);
        songApi.getSongsByKw(kw).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    songs.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                songs.postValue(null);
            }
        });
    }

}
