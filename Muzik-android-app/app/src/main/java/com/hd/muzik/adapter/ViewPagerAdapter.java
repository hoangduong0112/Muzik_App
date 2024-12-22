package com.hd.muzik.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hd.muzik.fragments.AlbumFragment;
import com.hd.muzik.fragments.ArtistFragment;
import com.hd.muzik.fragments.PlaylistFragment;
import com.hd.muzik.fragments.SongFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new AlbumFragment();
            case 2: return new PlaylistFragment();
            case 3: return new ArtistFragment();
            default: return new SongFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
