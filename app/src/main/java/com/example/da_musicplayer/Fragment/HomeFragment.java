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

import com.example.da_musicplayer.Adapter.AlbumAdapter;
import com.example.da_musicplayer.Adapter.ArtistAdapter;
import com.example.da_musicplayer.Adapter.SongAdapter;
import com.example.da_musicplayer.Data.TopAlbumsData;
import com.example.da_musicplayer.Data.TopArtistsData;
import com.example.da_musicplayer.Data.TopSongsData;
import com.example.da_musicplayer.Define.Album;
import com.example.da_musicplayer.Define.Artist;
import com.example.da_musicplayer.Define.Song;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.example.da_musicplayer.Interface.ArtistsCallback;
import com.example.da_musicplayer.Interface.SongsCallback;
import com.example.da_musicplayer.Manager.FavoriteManager;
import com.example.da_musicplayer.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment{
    RecyclerView recyclerView;
    ArrayList<Album> albumList;
    ArrayList<Artist> artistList;
    ArrayList<Song> songList;
    AlbumAdapter adapter_albums;
    ArtistAdapter adapter_artists;
    SongAdapter adapter_songs;
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
        TopAlbumsData.generateAlbumsData(new AlbumsCallback() {
            @Override
            public void onAlbumsLoaded(ArrayList<Album> albums) {
                albumList = albums;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView = view.findViewById(R.id.topAlbums_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_albums = new AlbumAdapter(getContext(), albumList, favoriteManager);
                recyclerView.setAdapter(adapter_albums);
            }

            @Override
            public void onAlbumsLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        });

        TopArtistsData.generateArtist(new ArtistsCallback() {
            @Override
            public void onArtistsLoaded(ArrayList<Artist> artists) {
                artistList = artists;
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView = view.findViewById(R.id.topArtist_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_artists = new ArtistAdapter(getActivity().getApplicationContext(), artistList);
                recyclerView.setAdapter(adapter_artists);
            }

            @Override
            public void onArtistsLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        });

        TopSongsData.generateSongs(new SongsCallback() {
            @Override
            public void onSongsLoaded(ArrayList<Song> songs) {
                songList = songs;
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
                recyclerView = view.findViewById(R.id.topSongs_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                adapter_songs = new SongAdapter(getActivity().getApplicationContext(), songList);
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