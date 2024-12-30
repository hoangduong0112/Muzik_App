package com.hd.muzik.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Playlist;
import com.hd.muzik.utils.OnSongClickListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private List<Playlist> playlists = new ArrayList<>();
    private SongAdapter songAdapter;
    private final OnSongClickListener listener;
    private final OnPlaylistClickListener playlistListener;

    public PlaylistListAdapter(OnSongClickListener listener, OnPlaylistClickListener playlistListener) {
        this.listener = listener;
        this.playlistListener = playlistListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.clear();
        this.playlists.addAll(playlists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(v);
    }
    public interface OnPlaylistClickListener {
        void onUpdatePlaylist(Playlist playlist);
        void onDeletePlaylist(Playlist playlist);
    }
    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.name.setText(playlist.getName());
        holder.recyclerViewSong.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        SongAdapter songAdapter = new SongAdapter(listener);
        songAdapter.setSongs(playlist.getSongs());
        holder.recyclerViewSong.setAdapter(songAdapter);
        holder.btnUpdatePlaylist.setOnClickListener(v -> playlistListener.onUpdatePlaylist(playlist));

        // Xử lý nút Delete
        holder.btnDelPlaylist.setOnClickListener(v ->  playlistListener.onDeletePlaylist(playlist));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }


}