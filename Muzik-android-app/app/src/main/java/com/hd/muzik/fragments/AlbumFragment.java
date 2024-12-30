package com.hd.muzik.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hd.muzik.R;
import com.hd.muzik.adapter.AlbumAdapter;
import com.hd.muzik.adapter.AlbumViewHolder;
import com.hd.muzik.services.AlbumViewModel;
import com.hd.muzik.services.MusicPlayerViewModel;
import com.hd.muzik.utils.OnSongClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnSongClickListener onSongClickListener;


    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private MusicPlayerViewModel musicPlayerViewModel;

    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
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
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_album);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);  // 2 cột
        recyclerView.setLayoutManager(layoutManager);

        musicPlayerViewModel = new ViewModelProvider(requireActivity()).get(MusicPlayerViewModel.class);
        AlbumViewModel albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);
        albumViewModel.getAlbums().observe(getViewLifecycleOwner(), albums -> {
            if (albums != null) {
                if (albumAdapter == null) {
                    albumAdapter = new AlbumAdapter(onSongClickListener);
                    recyclerView.setAdapter(albumAdapter);
                }
            }
            albumAdapter.setAlbums(albums);
        });
        albumViewModel.fetchAlbums();
        return view;
    }   


}