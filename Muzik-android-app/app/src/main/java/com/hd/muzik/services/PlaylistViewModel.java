package com.hd.muzik.services;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hd.muzik.model.Playlist;
import com.hd.muzik.model.PlaylistRequest;
import com.hd.muzik.model.Song;
import com.hd.muzik.retrofit.PlaylistApi;
import com.hd.muzik.retrofit.RetrofitInstance;
import com.hd.muzik.utils.TokenManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistViewModel extends ViewModel {
    private final MutableLiveData<List<Playlist>> playlists = new MutableLiveData<>();

    public LiveData<List<Playlist>> getPlaylist(){
        return playlists;
    }
    private final Context context;

    public PlaylistViewModel(Context context) {
        this.context = context.getApplicationContext();
        fetchPlaylists();
    }

    public void fetchPlaylists(){

        PlaylistApi playlistApi = RetrofitInstance.getInstanceWithAuth(context).create(PlaylistApi.class);
        playlistApi.getPlaylist().enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    playlists.postValue(response.body());
                    Log.d("success", "success");
                }
                else
                    Log.d("fail", "fail");
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                playlists.postValue(null);
                Log.d("fail2", "fail2");
            }
        });
    }

    public void addNewPlaylist(PlaylistRequest playlistRequest) {
        PlaylistApi playlistApi = RetrofitInstance.getInstanceWithAuth(context).create(PlaylistApi.class);
        playlistApi.createNewPlaylist(playlistRequest).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Playlist> updatedPlaylists = playlists.getValue();
                    if (updatedPlaylists != null) {
                        updatedPlaylists.add(response.body());
                        playlists.postValue(updatedPlaylists); // Thông báo LiveData
                    }
                    Log.d("success", "Playlist added successfully");
                } else {
                    Log.d("fail", "Failed to add playlist");
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d("fail2", "Failed to add playlist: " + t.getMessage());
            }
        });
    }

    public void updatePlaylist(int playlistId, String newName) {
        PlaylistApi playlistApi = RetrofitInstance.getInstanceWithAuth(context).create(PlaylistApi.class);

        PlaylistRequest request = new PlaylistRequest(newName);
        playlistApi.updatePlaylist(request, playlistId).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Cập nhật danh sách playlist
                    List<Playlist> currentPlaylists = playlists.getValue();
                    if (currentPlaylists != null) {
                        for (Playlist p : currentPlaylists) {
                            if (p.getId() == playlistId) {
                                p.setName(newName);
                                break;
                            }
                        }
                        playlists.postValue(currentPlaylists);
                    }
                } else {
                    Log.d("Update", "Failed to update playlist");
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d("Update", "Failed to update playlist: " + t.getMessage());
            }
        });
    }

    public void deletePlaylist(int playlistId) {
        PlaylistApi playlistApi = RetrofitInstance.getInstanceWithAuth(context).create(PlaylistApi.class);

        playlistApi.deletePlaylist(playlistId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchPlaylists();
                } else {
                    Log.d("PlaylistViewModel", "Failed to delete playlist");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PlaylistViewModel", "Delete playlist failed: " + t.getMessage());
            }
        });
    }
    public void addSongToPlaylist(int songId, int playlistId) {
        PlaylistApi playlistApi = RetrofitInstance.getInstanceWithAuth(context).create(PlaylistApi.class);

        playlistApi.addSongToPlaylist(playlistId, songId).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Playlist updatedPlaylist = response.body();

                    // Cập nhật danh sách playlist
                    List<Playlist> currentPlaylists = playlists.getValue();
                    if (currentPlaylists != null) {
                        for (int i = 0; i < currentPlaylists.size(); i++) {
                            if (currentPlaylists.get(i).getId() == playlistId) {
                                currentPlaylists.set(i, updatedPlaylist); // Cập nhật playlist
                                break;
                            }
                        }
                        playlists.postValue(currentPlaylists); // Thông báo LiveData
                    }

                    // Hiển thị thông báo
                    Toast.makeText(context, "Song added to playlist successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to add song to playlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
