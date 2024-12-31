package com.hd.muzik.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hd.muzik.R;
import com.hd.muzik.utils.OnSongClickListener;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    RecyclerView recyclerViewPlaylist, recyclerViewSong;
    TextView name, title_song, artist;
    FloatingActionButton addNewPlaylist;
    ImageButton btnUpdatePlaylist, btnDelPlaylist;
    public PlaylistViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerViewPlaylist = itemView.findViewById(R.id.playlist_recycler_view);
        recyclerViewSong = itemView.findViewById(R.id.playlist_song_recycle_view);

        name = itemView.findViewById(R.id.playlist_list_name);
        title_song = itemView.findViewById(R.id.song_name);
        artist = itemView.findViewById(R.id.song_artist_name);
        addNewPlaylist = itemView.findViewById(R.id.btn_add_new_playlist);
        btnUpdatePlaylist = itemView.findViewById(R.id.btn_update_playlist);
        btnDelPlaylist = itemView.findViewById(R.id.btn_delete_playlist);
    }


}
