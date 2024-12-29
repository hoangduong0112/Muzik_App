package com.hd.muzik.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.hd.muzik.R;
import com.hd.muzik.adapter.MainViewPaperAdapter;
import com.hd.muzik.fragments.SongDetailFragment;
import com.hd.muzik.model.Song;
import com.hd.muzik.services.MusicPlayerViewModel;
import com.hd.muzik.services.OnSongClickListener;
import com.hd.muzik.utils.MusicPlayerUtils;
import com.hd.muzik.utils.TokenManager;

public class MainActivity extends AppCompatActivity implements OnSongClickListener {
    private MaterialToolbar topAppBar;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private TextView songTitle, artistName;
    private ImageView albumArt;
    private LinearProgressIndicator playerProgress;
    private ImageButton playPauseButton;
    private LinearLayout playerController; // Đảm bảo khởi tạo LinearLayout này

    private MusicPlayerViewModel musicPlayerViewModel;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setupBottomNavigationAndViewPager();

        musicPlayerViewModel = new ViewModelProvider(this).get(MusicPlayerViewModel.class);

        playPauseButton.setOnClickListener(v -> togglePlayPause());

        // Đăng ký OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
                if (fragment != null && fragment.isVisible()) {
                    findViewById(R.id.detail_fragment).setVisibility(View.GONE);
                    findViewById(R.id.view_pager_main).setVisibility(View.VISIBLE);
                    getSupportFragmentManager().popBackStack();
                } else {
                    // Nếu không, kết thúc hoạt động
                    finish();
                }
            }
        });
    }

    private void initUI() {
        songTitle = findViewById(R.id.player_song_title);
        artistName = findViewById(R.id.player_artist_name);
        albumArt = findViewById(R.id.player_album_art);
        playerProgress = findViewById(R.id.player_progress);
        playPauseButton = findViewById(R.id.player_play_pause_button);
        topAppBar = findViewById(R.id.top_app_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager_main);
        playerController = findViewById(R.id.player_controller);
        setSupportActionBar(topAppBar);

        playerController.setOnClickListener(view -> {
            showSongDetailFragment();
        });
    }

    private void setupBottomNavigationAndViewPager() {
        MainViewPaperAdapter adapter = new MainViewPaperAdapter(this);
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_search:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_library:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.nav_profile:
                    viewPager.setCurrentItem(3);
                    break;
                default:
                    viewPager.setCurrentItem(0);
                    break;
            }
            return true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_search);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.nav_library);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                        break;
                }
            }
        });
    }
    private void startUpdatingProgress() {
        progressHandler.post(progressUpdater);
    }

    private void stopUpdatingProgress() {
        progressHandler.removeCallbacks(progressUpdater);
    }
    private void togglePlayPause() {
        if (MusicPlayerUtils.isPlaying()) {
            MusicPlayerUtils.pauseSong();
            stopUpdatingProgress();
        } else {
            MusicPlayerUtils.resumeSong();
            startUpdatingProgress(); // Đảm bảo gọi hàm này
        }
        updatePlayerUI(MusicPlayerUtils.getCurrentSong(), MusicPlayerUtils.isPlaying());
    }

    private void showPlayer(Song song) {
        if (song == null) return;

        MusicPlayerUtils.playSong(this, song);
        updatePlayerUI(song, true);

        // Cập nhật ViewModel
        musicPlayerViewModel.setCurrentSong(song);
        musicPlayerViewModel.setIsPlaying(true);

        startUpdatingProgress();
        playerController.setVisibility(View.VISIBLE);
    }

    private void showSongDetailFragment() {
        Song currentSong = MusicPlayerUtils.getCurrentSong();

        if (currentSong != null) {
            int songId = currentSong.getId(); // Assuming Song has an ID field

            SongDetailFragment fragment = SongDetailFragment.newInstance(songId);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.detail_fragment, fragment); // Sử dụng FragmentContainerView
            transaction.addToBackStack(null); // Optional: to allow "back" navigation
            transaction.commit();

            findViewById(R.id.detail_fragment).setVisibility(View.VISIBLE); // Hiển thị container
            findViewById(R.id.view_pager_main).setVisibility(View.GONE); // Ẩn ViewPager2
        }
    }



    @Override
    public void onSongClick(Song song) {
        showPlayer(song);
    }

    private void updatePlayerUI(Song song, boolean isPlaying) {
        if (song == null) return; // Tránh lỗi nếu song là null
        songTitle.setText(song.getName());
        artistName.setText(song.getArtistName());
        albumArt.setImageResource(R.drawable.avt_default); // Thay bằng ảnh album thực tế nếu có
        playPauseButton.setImageResource(isPlaying ?
                R.drawable.pause_24dp_000000_fill0_wght400_grad0_opsz24 :
                R.drawable.play_arrow_24dp_000000_fill0_wght400_grad0_opsz24);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopUpdatingProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên nếu cần
    }

    private final Handler progressHandler = new Handler();
    private final Runnable progressUpdater = new Runnable() {
        @Override
        public void run() {
            progressHandler.post(() -> {
                if (MusicPlayerUtils.isPlaying()) {
                    int duration = MusicPlayerUtils.getDuration();
                    int currentPosition = MusicPlayerUtils.getCurrentPosition();

                    if (duration > 0) {
                        int progress = (currentPosition * 100) / duration;
                        playerProgress.setProgress(progress);

                        // Cập nhật ViewModel
                        musicPlayerViewModel.setProgress(progress);
                    }
                    progressHandler.postDelayed(this, 500);
                }
            });
        }
    };


}
