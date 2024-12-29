package com.hd.muzik.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Song;

public class MusicPlayerViewModel extends ViewModel {
    private final MutableLiveData<Song> currentSong = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private final MutableLiveData<Integer> progress = new MutableLiveData<>();

    public LiveData<Song> getCurrentSong() {
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

    public LiveData<Integer> getProgress() {
        return progress;
    }

    public void setProgress(int progressValue) {
        progress.setValue(progressValue);
    }
}
