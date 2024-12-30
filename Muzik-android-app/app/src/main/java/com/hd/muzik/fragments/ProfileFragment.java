package com.hd.muzik.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.hd.muzik.R;
import com.hd.muzik.activities.MainActivity;
import com.hd.muzik.utils.TokenManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private TokenManager tokenManager;
    private MaterialButton logoutButton;
    private LogoutListener logoutListener;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public interface LogoutListener {
        void onLogout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LogoutListener) {
            logoutListener = (LogoutListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LogoutListener");
        }

        // Khởi tạo TokenManager ở đây thay vì onCreateView
        tokenManager = new TokenManager(requireContext());
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = v.findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(view -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onLogout();
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logoutListener = null;
    }
}