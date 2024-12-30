package com.hd.muzik.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.hd.muzik.R;


public class AlbumViewHolder extends RecyclerView.ViewHolder {
    ImageView imageUrl;
    TextView name, artistName;
    TextView countSong;
    RecyclerView recyclerViewSong;  // RecyclerView chứa danh sách bài hát
    LinearLayout songListContainer; // LinearLayout chứa RecyclerView
    MaterialButton playAlbumButton; // Nút Show/Hide Songs

    public AlbumViewHolder(View itemView) {
        super(itemView);
        imageUrl = itemView.findViewById(R.id.album_image);
        name = itemView.findViewById(R.id.album_name);
        countSong = itemView.findViewById(R.id.album_song_count);
        artistName = itemView.findViewById(R.id.album_artist_name);
        recyclerViewSong = itemView.findViewById(R.id.recycler_view_song);
        recyclerViewSong.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        songListContainer = itemView.findViewById(R.id.song_list_container);
        playAlbumButton = itemView.findViewById(R.id.play_album_button);
    }
}

