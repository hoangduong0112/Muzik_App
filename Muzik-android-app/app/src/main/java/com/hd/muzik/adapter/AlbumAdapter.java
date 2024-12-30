package com.hd.muzik.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Album;
import com.hd.muzik.model.Song;
import com.hd.muzik.utils.ImageLoader;
import com.hd.muzik.utils.MusicPlayerUtils;
import com.hd.muzik.utils.OnSongClickListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private List<Album> albums = new ArrayList<>();
    private SongAdapter songAdapter; // Để chứa và quản lý danh sách bài hát
    private final OnSongClickListener listener;


    public AlbumAdapter(OnSongClickListener listener) {
        this.listener = listener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setAlbums(List<Album> newAlbums) {
        this.albums.clear();
        this.albums.addAll(newAlbums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.name.setText(album.getName());
        holder.countSong.setText(String.valueOf(album.getCountSong()));
        holder.artistName.setText(album.getArtistName());
        if (album.getUrl() != null) {
            ImageLoader.loadImage(holder.imageUrl, album.getUrl());
        }

        holder.playAlbumButton.setOnClickListener(v -> {
            // Hiển thị/ẩn danh sách bài hát
            if (holder.songListContainer.getVisibility() == View.GONE) {
                holder.songListContainer.setVisibility(View.VISIBLE);
                holder.playAlbumButton.setText("Hide Songs");

                // Tạo adapter và gán vào RecyclerView
                songAdapter = new SongAdapter(listener);
                Log.d("AlbumAdapter", "Album Songs: " + album.getSongs().size());
                songAdapter.setSongs(album.getSongs());
                holder.recyclerViewSong.setAdapter(songAdapter);
            } else {
                holder.songListContainer.setVisibility(View.GONE);
                holder.playAlbumButton.setText("Show Songs");
            }
        });

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
