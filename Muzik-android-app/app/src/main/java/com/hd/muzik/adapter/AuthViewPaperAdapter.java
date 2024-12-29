package com.hd.muzik.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.hd.muzik.fragments.SignInFragment;
import com.hd.muzik.fragments.SignUpFragment;


public class AuthViewPaperAdapter extends FragmentStateAdapter {

    public AuthViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new SignUpFragment();

            default: return new SignInFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
