package com.hd.muzik.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hd.muzik.R;
import com.hd.muzik.activities.MainActivity;
import com.hd.muzik.model.User;
import com.hd.muzik.retrofit.LoginApi;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private TokenManager tokenManager;
    private MaterialButton logoutButton;
    private LogoutListener logoutListener;
    private User currentUser;
    private ImageView profileImageView;
    private TextView fullNameTextView, usernameTextView, createdAtTextView;


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

        profileImageView = v.findViewById(R.id.profile_avatar_image);
        fullNameTextView = v.findViewById(R.id.fullNameTextView);
        usernameTextView = v.findViewById(R.id.usernameTextView);
        createdAtTextView = v.findViewById(R.id.createdAtTextView);
        logoutButton.setOnClickListener(view -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onLogout();
            }
        });
        getUserInfo();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logoutListener = null;
    }

    private void getUserInfo() {
        LoginApi loginApi = RetrofitInstance.getInstanceWithAuth(getContext()).create(LoginApi.class);
        loginApi.getCurrentUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    currentUser = response.body();
                    updateUI(currentUser);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle error
            }
        });
    }

    private void updateUI(User user) {
        if (user != null) {
            fullNameTextView.setText(user.getFullName());
            usernameTextView.setText(user.getUsername());
            createdAtTextView.setText("Joined: " + user.getCreatedAt());

            // Set avatar image if available, else default avatar
            if (user.getAvatar() != null) {
                Glide.with(this).load(user.getAvatar()).into(profileImageView);
            } else {
                profileImageView.setImageResource(R.drawable.avt_default);
            }
        }
    }
}
