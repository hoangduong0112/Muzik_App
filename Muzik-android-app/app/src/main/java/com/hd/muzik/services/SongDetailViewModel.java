package com.hd.muzik.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Song;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.retrofit.SongApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongDetailViewModel extends ViewModel {
    MutableLiveData<Song> currentSong = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPlaying = new MutableLiveData<>(false);
    public LiveData<Song> getCurrentSong(){
        return currentSong;
    }

    public void setCurrentSong(Song song) {
        currentSong.setValue(song);
    }

    public LiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean playing) {
        isPlaying.setValue(playing);
    }

    public void fetchSongById(int id){

        SongApi songApi = RetrofitInstance.getInstance().create(SongApi.class);
        songApi.getSongById(id).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (response.isSuccessful() && response.body() != null){
                    setCurrentSong(response.body());
                }
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Log.d("Error", "Lỗi lấy dữ liệu");
            }
        });
    }
}
