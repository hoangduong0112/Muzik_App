package com.hd.muzik.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hd.muzik.fragments.SongDetailFragment;
import com.hd.muzik.model.Song;

import java.util.List;

public class SongDetailAdapter extends FragmentStateAdapter {
    private List<Song> songs;

    public SongDetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SongDetailAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public SongDetailAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Trả về fragment chi tiết bài hát cho mỗi position
//        return SongDetailFragment.newInstance(songs.get(position));
        return null;
    }

    @Override
    public int getItemCount() {
        return songs.size();  // Số lượng fragment
    }
}
