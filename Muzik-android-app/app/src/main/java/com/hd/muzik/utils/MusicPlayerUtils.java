package com.hd.muzik.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.hd.muzik.model.Song;

import lombok.Getter;

public class MusicPlayerUtils {
    private static MediaPlayer mediaPlayer;

    @Getter
    private static Song currentSong; // Lưu bài hát hiện tại
    private static final String TAG = "MusicPlayerUtils";

    public interface MusicPlayerListener {
        void onSongPrepared(); // Được gọi khi bài hát đã sẵn sàng và bắt đầu phát
        void onSongCompleted();
        void onError(String errorMessage);
        void onPlayPauseChanged(boolean isPlaying);
    }

    private static MusicPlayerListener listener;

    public static void setMusicPlayerListener(MusicPlayerListener musicPlayerListener) {
        listener = musicPlayerListener;
    }

    public static void playSong(Context context, Song song) {
        stopSong(); // Dừng bài hát nếu đang phát

        try {
            mediaPlayer = new MediaPlayer();
            currentSong = song; // Cập nhật bài hát

            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start(); // Bắt đầu phát nhạc
                if (listener != null) {
                    listener.onSongPrepared(); // Thông báo bài hát đã chuẩn bị
                    listener.onPlayPauseChanged(true); // Thông báo đang phát nhạc
                }
            });

            mediaPlayer.setOnCompletionListener(mp -> {
                if (listener != null) listener.onSongCompleted(); // Bài hát hoàn thành
                if (listener != null) listener.onPlayPauseChanged(false); // Thông báo nút play
            });

            mediaPlayer.setDataSource(context, Uri.parse(song.getSongUrl()));
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            if (listener != null) listener.onError("Error: " + e.getMessage());
        }
    }




    public static void stopSong() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                releaseMediaPlayer();
                currentSong = null; // Xóa bài hát hiện tại
            } catch (IllegalStateException e) {
                Log.e(TAG, "Error stopping song: " + e.getMessage(), e);
            }
        }
    }

    public static void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            if (listener != null) listener.onPlayPauseChanged(false); // Cập nhật trạng thái nút pause
        }
    }

    public static void resumeSong() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            if (listener != null) listener.onPlayPauseChanged(true); // Cập nhật trạng thái nút play
        }
    }

    public static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private static void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void togglePlayPause(Context context) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                pauseSong(); // Dừng bài hát nếu đang phát
            } else {
                resumeSong(); // Tiếp tục bài hát nếu đang dừng
            }
        }
    }
    public static int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration(); // Trả về tổng độ dài (ms)
        }
        return 0;
    }


    public static int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

}
