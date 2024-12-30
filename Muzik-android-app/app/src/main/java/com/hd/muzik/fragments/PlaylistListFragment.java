package com.hd.muzik.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hd.muzik.R;
import com.hd.muzik.adapter.PlaylistListAdapter;
import com.hd.muzik.model.Playlist;
import com.hd.muzik.model.PlaylistRequest;
import com.hd.muzik.services.MusicPlayerViewModel;
import com.hd.muzik.services.PlaylistViewModel;
import com.hd.muzik.services.PlaylistViewModelFactory;
import com.hd.muzik.utils.OnSongClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistListFragment extends Fragment implements PlaylistListAdapter.OnPlaylistClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView playlistRecycleView;
    private OnSongClickListener onSongClickListener;
    private PlaylistListAdapter playlistListAdapter;
    private PlaylistViewModel playlistViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public PlaylistListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistListFragment newInstance(String param1, String param2) {
        PlaylistListFragment fragment = new PlaylistListFragment();
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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSongClickListener) {
            onSongClickListener = (OnSongClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSongClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onSongClickListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playlist_list, container, false);
        playlistRecycleView = v.findViewById(R.id.playlist_recycler_view);

        // Khởi tạo LayoutManager
        playlistRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo Adapter
        playlistListAdapter = new PlaylistListAdapter(onSongClickListener, this);
        playlistRecycleView.setAdapter(playlistListAdapter);

        // Sử dụng ViewModelFactory để khởi tạo PlaylistViewModel
        playlistViewModel = new ViewModelProvider(this, new PlaylistViewModelFactory(requireContext())).get(PlaylistViewModel.class);

        // Quan sát dữ liệu và cập nhật Adapter
        playlistViewModel.getPlaylist().observe(getViewLifecycleOwner(), playlists -> {
            if (playlists != null && !playlists.isEmpty()) {
                playlistListAdapter.setPlaylists(playlists);
            } else {
                Log.d("PlaylistListFragment", "Playlists is empty or null");
            }
        });

        FloatingActionButton fab = v.findViewById(R.id.btn_add_new_playlist);
        fab.setOnClickListener(s -> showAddPlaylistDialog());

        // Fetch dữ liệu
        playlistViewModel.fetchPlaylists();

        return v;
    }

    private void createPlaylist(String playlistName) {
        PlaylistRequest request = new PlaylistRequest(playlistName);
        playlistViewModel.addNewPlaylist(request);

        playlistViewModel.getPlaylist().observe(this, playlists -> {
            playlistListAdapter.setPlaylists(playlists);
        });
    }
    private void showAddPlaylistDialog() {
        // Inflate layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_playlist, null);
        EditText etPlaylistName = dialogView.findViewById(R.id.et_playlist_name);
        Button btnCreate = dialogView.findViewById(R.id.btn_create_playlist);

        // Create AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("Add New Playlist")
                .setCancelable(true)
                .create();

        btnCreate.setOnClickListener(v -> {
            String playlistName = etPlaylistName.getText().toString().trim();
            if (!playlistName.isEmpty()) {
                createPlaylist(playlistName);
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter a playlist name", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    @Override
    public void onUpdatePlaylist(Playlist playlist) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_playlist, null);
        EditText etPlaylistName = dialogView.findViewById(R.id.et_playlist_name);
        Button btnUpdate = dialogView.findViewById(R.id.btn_create_playlist);

        // Đặt tên hiện tại vào EditText
        etPlaylistName.setText(playlist.getName());

        // Tạo AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("Update Playlist")
                .setCancelable(true)
                .create();

        // Đổi text của nút thành "Update"
        btnUpdate.setText("Update");
        btnUpdate.setOnClickListener(v -> {
            String updatedName = etPlaylistName.getText().toString().trim();
            if (!updatedName.isEmpty()) {
                playlistViewModel.updatePlaylist(playlist.getId(), updatedName);
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter a valid playlist name", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    public void onDeletePlaylist(Playlist playlist) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Playlist")
                .setMessage("Are you sure you want to delete this playlist?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    playlistViewModel.deletePlaylist(playlist.getId());
                    Toast.makeText(getContext(), "Playlist deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

}