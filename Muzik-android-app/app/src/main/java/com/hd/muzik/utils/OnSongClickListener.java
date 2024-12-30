package com.hd.muzik.utils;

import com.hd.muzik.model.Song;

public interface OnSongClickListener {
    void onSongClick(Song song);
    void onAddToPlaylist(Song song);
}
