package com.hd.muzik.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hd.muzik.fragments.AlbumFragment;
import com.hd.muzik.fragments.ArtistFragment;
import com.hd.muzik.fragments.SongListFragment;

public class HomeViewPaperAdapter extends FragmentStateAdapter {
    public HomeViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HomeViewPaperAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public HomeViewPaperAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new AlbumFragment();
            case 2: return new ArtistFragment();
            default: return new SongListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
