package com.hd.muzik.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    ImageView imageUrl;
    TextView name, artistName;
    TextView countSong;

    public AlbumViewHolder(@NonNull View itemView) {
        super(itemView);
        imageUrl = itemView.findViewById(R.id.album_image);
        name = itemView.findViewById(R.id.album_name);
        countSong = itemView.findViewById(R.id.album_song_count);
        artistName = itemView.findViewById(R.id.album_artist_name);
    }
}
