package com.hd.muzik.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hd.muzik.R;
import com.hd.muzik.activities.AuthActivity;
import com.hd.muzik.activities.MainActivity;
import com.hd.muzik.model.LoginRequest;
import com.hd.muzik.retrofit.LoginApi;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.utils.TokenManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TokenManager tokenManager;
    private TextInputEditText mUsername, mPassword;
    private MaterialButton mLogin;


    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        tokenManager = new TokenManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // Tìm view trong rootView
        mLogin = rootView.findViewById(R.id.loginButton);
        mUsername = rootView.findViewById(R.id.user_login_id);
        mPassword = rootView.findViewById(R.id.password_login_id);

        // Thiết lập sự kiện cho nút đăng nhập
        mLogin.setOnClickListener(view -> {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            loginUser(username, password);
        });

        return rootView;
    }

    private void loginUser(String username, String password){
        LoginApi loginApi = RetrofitInstance.getInstance().create(LoginApi.class);
        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<ResponseBody> call = loginApi.login(loginRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String jwtToken = response.body().string(); // Lấy token từ ResponseBody
                        tokenManager.saveToken(jwtToken);

                        navigateToMainActivity();
                    } else {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }catch (IOException ex){
                    Toast.makeText(requireContext(), "Error ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("SignInFragment", "Login Failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }


}