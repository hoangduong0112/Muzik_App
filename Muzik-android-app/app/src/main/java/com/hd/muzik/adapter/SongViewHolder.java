package com.hd.muzik.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;

public class SongViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView artist;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.song_title);
        artist = itemView.findViewById(R.id.song_artist);
    }
}
