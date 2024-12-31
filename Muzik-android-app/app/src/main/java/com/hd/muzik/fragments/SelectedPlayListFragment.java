package com.hd.muzik.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hd.muzik.R;
import com.hd.muzik.adapter.PlaylistListAdapter;
import com.hd.muzik.adapter.SelectPlaylistAdapter;
import com.hd.muzik.model.Song;
import com.hd.muzik.services.PlaylistViewModel;
import com.hd.muzik.services.PlaylistViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedPlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedPlayListFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SONG_ID = "song_id";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlaylistViewModel playlistViewModel;
    private int songId;

    public SelectedPlayListFragment(){

    }


    public static SelectedPlayListFragment newInstance(int songId) {
        SelectedPlayListFragment fragment = new SelectedPlayListFragment();
        Bundle args = new Bundle();
        args.putInt("SONG_ID", songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songId = getArguments().getInt("SONG_ID");
        }
        PlaylistViewModelFactory factory = new PlaylistViewModelFactory(requireContext());
        playlistViewModel = new ViewModelProvider(this, factory).get(PlaylistViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_play_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_playlists);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        SelectPlaylistAdapter adapter = new SelectPlaylistAdapter(playlist -> {
            playlistViewModel.addSongToPlaylist(songId, playlist.getId());
            dismiss(); // Đóng dialog sau khi chọn Playlist
            Toast.makeText(requireContext(), "Song added to " + playlist.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        playlistViewModel.getPlaylist().observe(this, adapter::setPlaylists);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        playlistViewModel.fetchPlaylists();
    }

}