package com.hd.muzik.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.fragments.SongDetailFragment;
import com.hd.muzik.model.Song;
import com.hd.muzik.services.OnSongClickListener;
import com.hd.muzik.utils.MusicPlayerUtils;


import java.util.ArrayList;
import java.util.List;



public class SongListAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private final List<Song> songs = new ArrayList<>();
    private final OnSongClickListener onSongClickListener;

    public SongListAdapter(OnSongClickListener listener) {
        this.onSongClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSongs(List<Song> newSongs) {
        songs.clear();
        songs.addAll(newSongs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view, onSongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}