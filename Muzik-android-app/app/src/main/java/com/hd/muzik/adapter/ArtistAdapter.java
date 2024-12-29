package com.hd.muzik.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Album;
import com.hd.muzik.model.Artist;
import com.hd.muzik.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder>{

    private List<Artist> artists = new ArrayList<>();

    public void setArtists(List<Artist> newArtists) {
        this.artists.clear();
        this.artists.addAll(newArtists);
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.name.setText(artist.getName());

        if (artist.getPhotoUrl() != null) {
            ImageLoader.loadImage(holder.photoUrl, artist.getPhotoUrl());
        }
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
