package com.example.da_musicplayer.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.da_musicplayer.Adapter.TopAlbumsAdapter;
import com.example.da_musicplayer.Adapter.TopArtistsAdapter;
import com.example.da_musicplayer.Adapter.TopSongsAdapter;
import com.example.da_musicplayer.Data.AlbumsData;
import com.example.da_musicplayer.Data.ArtistsData;
import com.example.da_musicplayer.Data.SongsData;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Artists;
import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.example.da_musicplayer.Interface.ArtistsCallback;
import com.example.da_musicplayer.Interface.SongsCallback;
import com.example.da_musicplayer.Manager.FavoriteManager;
import com.example.da_musicplayer.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment{
    RecyclerView recyclerView;
    ArrayList<Albums> albumsList;
    ArrayList<Artists> artistsList;
    ArrayList<Songs> songsList;
    TopAlbumsAdapter adapter_albums;
    TopArtistsAdapter adapter_artists;
    TopSongsAdapter adapter_songs;
    private FavoriteManager favoriteManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteManager = new FavoriteManager(getContext());
        AlbumsData.generateAlbumsData(new AlbumsCallback() {
            @Override
            public void onAlbumsLoaded(ArrayList<Albums> albums) {
                albumsList = albums;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView = view.findViewById(R.id.topAlbums_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_albums = new TopAlbumsAdapter(getContext(), albumsList, favoriteManager);
                recyclerView.setAdapter(adapter_albums);
            }

            @Override
            public void onAlbumsLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        });

        ArtistsData.generateArtist(new ArtistsCallback() {
            @Override
            public void onArtistsLoaded(ArrayList<Artists> artists) {
                artistsList = artists;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView = view.findViewById(R.id.topArtist_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_artists = new TopArtistsAdapter(getActivity().getApplicationContext(), artistsList);
                recyclerView.setAdapter(adapter_artists);
            }

            @Override
            public void onArtistsLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        });

        SongsData.generateSongs(new SongsCallback() {
            @Override
            public void onSongsLoaded(ArrayList<Songs> songs) {
                songsList = songs;
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                recyclerView = view.findViewById(R.id.topSongs_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_songs = new TopSongsAdapter(getActivity().getApplicationContext(), songsList);
                recyclerView.setAdapter(adapter_songs);
            }

            @Override
            public void onSongsLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        });

    }
}