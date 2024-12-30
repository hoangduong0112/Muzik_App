package com.hd.muzik.services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlaylistViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public PlaylistViewModelFactory(Context context) {
        this.context = context.getApplicationContext();  // Safely get application context
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaylistViewModel.class)) {
            return (T) new PlaylistViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
