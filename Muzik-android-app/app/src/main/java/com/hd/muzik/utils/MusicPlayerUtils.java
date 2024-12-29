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
        void onSongCompleted();
        void onError(String errorMessage);
    }

    private static MusicPlayerListener listener;

    public static void setMusicPlayerListener(MusicPlayerListener musicPlayerListener) {
        listener = musicPlayerListener;
    }

    public static void playSong(Context context, Song song) {
        stopSong(); // Dừng bài hát hiện tại nếu có

        try {
            mediaPlayer = new MediaPlayer();
            currentSong = song; // Cập nhật bài hát hiện tại

            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Song completed");
                if (listener != null) listener.onSongCompleted();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                String errorMessage = "MediaPlayer error: what=" + what + ", extra=" + extra;
                Log.e(TAG, errorMessage);
                if (listener != null) listener.onError(errorMessage);
                return true;
            });

            if (song.getSongUrl().startsWith("http://") || song.getSongUrl().startsWith("https://")) {
                mediaPlayer.setDataSource(song.getSongUrl());
            } else {
                mediaPlayer.setDataSource(context, Uri.parse(song.getSongUrl()));
            }

            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e(TAG, "Error playing song: " + e.getMessage(), e);
            if (listener != null) listener.onError("Error playing song: " + e.getMessage());
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
        }
    }

    public static void resumeSong() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.start();
            } catch (IllegalStateException e) {
                Log.e(TAG, "Error resuming song: " + e.getMessage(), e);
            }
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

    public static int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration(); // Trả về tổng độ dài (ms)
        }
        return 0;
    }


    public static int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition(); // Trả về vị trí hiện tại (ms)
        }
        return 0;
    }

}
