package com.hd.muzik.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hd.muzik.R;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
    ImageView photoUrl;
    TextView name;
    public ArtistViewHolder(@NonNull View itemView) {
        super(itemView);
        photoUrl = itemView.findViewById(R.id.artist_image);
        name = itemView.findViewById(R.id.artist_name);

    }
}
