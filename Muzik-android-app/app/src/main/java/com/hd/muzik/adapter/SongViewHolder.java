package com.hd.muzik.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Song;
import com.hd.muzik.utils.OnSongClickListener;

public class SongViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView artist;
    ImageButton btnAddToPlaylist;
    private final OnSongClickListener onSongClickListener;

    public SongViewHolder(@NonNull View itemView, OnSongClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.song_name);
        artist = itemView.findViewById(R.id.song_artist_name);
        btnAddToPlaylist = itemView.findViewById(R.id.btn_song_add_to_playlist);
        this.onSongClickListener = listener;
    }

    void bind(Song song) {
        title.setText(song.getName());
        artist.setText(song.getArtistName());
        itemView.setOnClickListener(v -> {
            if (onSongClickListener != null) {
                onSongClickListener.onSongClick(song);
            }
        });

        btnAddToPlaylist.setOnClickListener(v ->{
            if (onSongClickListener != null) {
                onSongClickListener.onAddToPlaylist(song);
            }
        });



    }
}
