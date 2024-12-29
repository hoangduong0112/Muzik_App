package com.hd.muzik.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.hd.muzik.R;
import com.hd.muzik.model.Genre;
import com.hd.muzik.model.Song;
import com.hd.muzik.retrofit.LoginApi;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.retrofit.SongApi;
import com.hd.muzik.services.MusicPlayerViewModel;
import com.hd.muzik.utils.ImageLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SONG_ID  = "song_id";

    private static Song song;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private MusicPlayerViewModel musicPlayerViewModel;
    private boolean isRepeat = false;
    private boolean isFavorite = false;
    private LinearProgressIndicator progressIndicator;
    private Handler handler;
    public SongDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SongDetailFragment.
     */
    public static SongDetailFragment newInstance(int songId) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SONG_ID, songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicPlayerViewModel = new ViewModelProvider(requireActivity()).get(MusicPlayerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_detail, container, false);

        TextView songTitle = view.findViewById(R.id.song_detail_title);
        TextView songArtist = view.findViewById(R.id.song_detail_artist);
        ImageButton playPauseButton = view.findViewById(R.id.button_play_pause);
        LinearProgressIndicator progressIndicator = view.findViewById(R.id.progress_indicator);

        // Lắng nghe thay đổi từ ViewModel
        musicPlayerViewModel.getCurrentSong().observe(getViewLifecycleOwner(), song -> {
            if (song != null) {
                songTitle.setText(song.getName());
                songArtist.setText(song.getArtistName());
            }
        });

        musicPlayerViewModel.getIsPlaying().observe(getViewLifecycleOwner(), isPlaying -> {
            if (isPlaying != null) {
                playPauseButton.setImageResource(isPlaying ? R.drawable.pause_24dp_000000_fill0_wght400_grad0_opsz24
                        : R.drawable.play_arrow_24dp_000000_fill0_wght400_grad0_opsz24);
            }
        });

        musicPlayerViewModel.getProgress().observe(getViewLifecycleOwner(), progress -> {
            if (progress != null) {
                progressIndicator.setProgress(progress);
            }
        });

        playPauseButton.setOnClickListener(v -> togglePlayPause(playPauseButton));

        return view;
    }

    private void setupMediaPlayer() {
        try {
            mediaPlayer.setDataSource(song.getSongUrl());
            mediaPlayer.setOnPreparedListener(mp -> progressIndicator.setMax(mp.getDuration()));
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnCompletionListener(mp -> {
                if (!isRepeat) {
                    togglePlayPause(null);
                } else {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void togglePlayPause(ImageButton button) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            if (button != null) button.setImageResource(R.drawable.play_arrow_24dp_000000_fill0_wght400_grad0_opsz24);
        } else {
            mediaPlayer.start();
            isPlaying = true;
            if (button != null) button.setImageResource(R.drawable.pause_24dp_000000_fill0_wght400_grad0_opsz24);
            updateProgress();
        }
    }

    private void toggleRepeat(ImageButton button) {
        isRepeat = !isRepeat;
        button.setImageResource(isRepeat ? R.drawable.repeat_24dp_000000_fill0_wght400_grad0_opsz24
                : R.drawable.repeat_one_24dp_000000_fill0_wght400_grad0_opsz24);
    }

    private void toggleFavorite(ImageButton button) {
        isFavorite = !isFavorite;
        button.setImageResource(isFavorite ? R.drawable.heart_check_24dp_000000_fill0_wght400_grad0_opsz24
                : R.drawable.favorite_24dp_000000_fill0_wght400_grad0_opsz24);
    }

    private void updateProgress() {
        handler.postDelayed(() -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                progressIndicator.setProgress(mediaPlayer.getCurrentPosition());
                updateProgress();
            }
        }, 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}