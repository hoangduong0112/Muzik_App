package com.hd.muzik.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;
import com.hd.muzik.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class SelectPlaylistAdapter extends RecyclerView.Adapter<SelectPlaylistAdapter.ViewHolder> {

    private final List<Playlist> playlists = new ArrayList<>();
    private final OnPlaylistSelectListener listener;

    public SelectPlaylistAdapter(OnPlaylistSelectListener listener) {
        this.listener = listener;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.clear();
        this.playlists.addAll(playlists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.name.setText(playlist.getName());
        holder.itemView.setOnClickListener(v -> listener.onPlaylistSelected(playlist));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public interface OnPlaylistSelectListener {
        void onPlaylistSelected(Playlist playlist);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_playlist_name);
        }
    }
}