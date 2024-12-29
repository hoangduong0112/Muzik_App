package com.hd.muzik.adapter;

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
import com.hd.muzik.fragments.PlaylistFragment;
import com.hd.muzik.fragments.ProfileFragment;
import com.hd.muzik.fragments.SearchFragment;
import com.hd.muzik.fragments.SongDetailFragment;
import com.hd.muzik.utils.TokenManager;

public class MainViewPaperAdapter  extends FragmentStateAdapter {
    private TokenManager tokenManager;
    public MainViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        tokenManager = new TokenManager(fragmentActivity);
    }

    public MainViewPaperAdapter(@NonNull Fragment fragment) {
        super(fragment);
        tokenManager = new TokenManager(fragment.getContext());
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new SearchFragment();
            case 2: return tokenManager.isTokenValid() ? new PlaylistFragment() : new AuthRequireFragment();
            case 3: return tokenManager.isTokenValid() ? new ProfileFragment() : new AuthRequireFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}