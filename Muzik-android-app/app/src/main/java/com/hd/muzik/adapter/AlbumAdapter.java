package com.hd.muzik.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Album;
import com.hd.muzik.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    private List<Album> albums = new ArrayList<>();

//    public AlbumAdapter(Context context, List<Album> newAlbums) {
//        this.albums = newAlbums;
//    }
    @SuppressLint("NotifyDataSetChanged")
    public void setAlbums(List<Album> newAlbums) {
        this.albums.clear();
        this.albums.addAll(newAlbums);
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
    }
    @Override
    public int getItemCount() {
        return albums.size();
    }

}
