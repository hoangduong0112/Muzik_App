package com.hd.muzik.services;

import com.hd.muzik.model.Song;

public interface OnSongClickListener {
    void onSongClick(Song song);  // Phương thức xử lý khi bấm vào bài hát
    void onPause();  // Phương thức để xử lý dừng bài hát
    void onDestroy();  // Phương thức để xử lý khi hủy
}
