package com.hd.muzik.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Song;
import com.hd.muzik.utils.MusicPlayerUtils;
import com.hd.muzik.utils.OnSongClickListener;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private List<Song> songs = new ArrayList<>();
    private final OnSongClickListener listener;

    public SongAdapter(OnSongClickListener listener) {
        this.listener = listener;
    }

    // Phương thức để cập nhật danh sách bài hát
    @SuppressLint("NotifyDataSetChanged")
    public void setSongs(List<Song> newSongs) {
        this.songs.clear();
        this.songs.addAll(newSongs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.title.setText(song.getName());
        holder.artist.setText(song.getArtistName());
        holder.itemView.setOnClickListener(v -> {
            listener.onSongClick(song);
            MusicPlayerUtils.playSong(holder.itemView.getContext(), song);
        });

        holder.btnAddToPlaylist.setOnClickListener(view -> {
            listener.onAddToPlaylist(song);
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
