package com.hd.muzik.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hd.muzik.fragments.AlbumFragment;
import com.hd.muzik.fragments.ArtistFragment;
import com.hd.muzik.fragments.AuthRequireFragment;
import com.hd.muzik.fragments.HomeFragment;
import com.hd.muzik.fragments.PlaylistListFragment;
import com.hd.muzik.fragments.ProfileFragment;
import com.hd.muzik.fragments.SearchFragment;
import com.hd.muzik.fragments.SongDetailFragment;
import com.hd.muzik.utils.TokenManager;

public class MainViewPaperAdapter extends FragmentStateAdapter {
    private final TokenManager tokenManager;
    private boolean isTokenValid;

    public MainViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        tokenManager = new TokenManager(fragmentActivity);
        isTokenValid = tokenManager.isTokenValid(); // Khởi tạo trạng thái token
    }

    public MainViewPaperAdapter(@NonNull Fragment fragment) {
        super(fragment);
        tokenManager = new TokenManager(fragment.getContext());
        isTokenValid = tokenManager.isTokenValid(); // Khởi tạo trạng thái token
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTokenState(boolean isValid) {
        isTokenValid = isValid;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return isTokenValid ? new PlaylistListFragment() : new AuthRequireFragment();
            case 2:
                return new SearchFragment();
            case 3:
                return isTokenValid ? new ProfileFragment() : new AuthRequireFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Số lượng tab trong ViewPager2
    }
}
