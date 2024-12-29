package com.hd.muzik.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hd.muzik.R;
import com.hd.muzik.adapter.AlbumAdapter;
import com.hd.muzik.adapter.ArtistAdapter;
import com.hd.muzik.services.ArtistViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;
    public ArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistFragment newInstance(String param1, String param2) {
        ArtistFragment fragment = new ArtistFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artist_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_artist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArtistViewModel artistViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        artistViewModel.getArtists().observe(getViewLifecycleOwner(), artists -> {
            if(artists != null)
                if(artistAdapter == null){
                    artistAdapter = new ArtistAdapter();
                    recyclerView.setAdapter(artistAdapter);
                }
            artistAdapter.setArtists(artists);
        });
        artistViewModel.fetchArtist();
        return v;
    }
}