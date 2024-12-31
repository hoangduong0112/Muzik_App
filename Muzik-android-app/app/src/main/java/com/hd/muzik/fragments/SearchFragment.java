package com.hd.muzik.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.hd.muzik.R;
import com.hd.muzik.adapter.SongListAdapter;
import com.hd.muzik.model.Song;
import com.hd.muzik.services.SongListViewModel;
import com.hd.muzik.utils.OnSongClickListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private SongListViewModel songListViewModel;
    private SongListAdapter songListAdapter;
    private OnSongClickListener onSongClickListener;
    private RecyclerView recyclerView;
    private TextView noResultsTextView;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve parameters (if needed)
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        songListViewModel = new ViewModelProvider(this).get(SongListViewModel.class);


        recyclerView = view.findViewById(R.id.recycler_view_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        songListAdapter = new SongListAdapter(onSongClickListener);
        recyclerView.setAdapter(songListAdapter);

        noResultsTextView = view.findViewById(R.id.tv_no_results);

        songListViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> updateUI(songs));

        SearchBar searchBar = view.findViewById(R.id.search_bar);
        SearchView searchView = view.findViewById(R.id.search_view);

        searchView.setupWithSearchBar(searchBar);
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString().trim();
                if (!query.isEmpty()) {
                    songListViewModel.fetchSongsByKw(query);
                }
            }
        });

        return view;
    }

    private void updateUI(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noResultsTextView.setVisibility(View.GONE);
            songListAdapter.setSongs(songs);
        }
    }
}