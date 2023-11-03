package com.example.da_musicplayer.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.da_musicplayer.Adapter.SongOfAlbumAdapter;
import com.example.da_musicplayer.Adapter.TopAlbumsAdapter;
import com.example.da_musicplayer.Data.AlbumsData;
import com.example.da_musicplayer.Data.ListSongsData;
import com.example.da_musicplayer.Data.SongsData;
import com.example.da_musicplayer.Data.Songs_Item_Data;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.example.da_musicplayer.Interface.SongsCallback;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    SearchView searchAll_SearchFragment;
    RecyclerView albums_SearchFragment;
    RecyclerView songs_SearchFragment;
    ArrayList<Albums> albumsList;
    ArrayList<Songs_Item> songs_List;
    TopAlbumsAdapter adapter_albums;
    SongOfAlbumAdapter songOfAlbumAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            searchAll_SearchFragment = view.findViewById(R.id.searchAll_SearchFragment);
            searchAll_SearchFragment.clearFocus();

/////////////////////////////////////// Đổ all Albums /////////////////////////////////
        AlbumsData.generateAlbumsData(new AlbumsCallback() {
            @Override
            public void onAlbumsLoaded(ArrayList<Albums> albums) {
                albumsList = albums;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                albums_SearchFragment = view.findViewById(R.id.albums_SearchFragment);
                albums_SearchFragment.setLayoutManager(gridLayoutManager);
                adapter_albums = new TopAlbumsAdapter(getActivity().getApplicationContext(), albums);
                albums_SearchFragment.setAdapter(adapter_albums);
            }
            @Override
            public void onAlbumsLoadError(String errorMessage) {
            }
        });
/////////////////////////////////////// Đổ all Songs //////////////////////////////////
        ListSongsData.generatelistSongs(new SongsItemCallback() {
            @Override
            public void onSongsItemLoaded(ArrayList<Songs_Item> songsList) {
                songs_List = songsList;
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1);
                songs_SearchFragment = view.findViewById(R.id.songs_SearchFragment);
                songs_SearchFragment.setLayoutManager(gridLayoutManager1);
                songOfAlbumAdapter = new SongOfAlbumAdapter(getActivity().getApplicationContext(),songsList);
                songs_SearchFragment.setAdapter(songOfAlbumAdapter);
                songOfAlbumAdapter.setOnItemClickListener(new SongOfAlbumAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getContext(), Player.class);
                        intent.putExtra("song_from_searchFragment",songsList.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onSongsItemLoadError(String errorMessage) {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////
        searchAll_SearchFragment.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }
    private void filterList(String text){
            ArrayList<Songs_Item> filterList_Song = new ArrayList<>();
            ArrayList<Albums> filterList_Album = new ArrayList<>();
        for (Songs_Item songsItem: songs_List){
            if(songsItem.getTitle_song().toLowerCase().contains(text.toLowerCase())){
                filterList_Song.add(songsItem);
            }
        }

        for(Albums album: albumsList){;
            if(album.getName().toLowerCase().contains(text.toLowerCase())){
                filterList_Album.add(album);
            }
        }

        if (!(filterList_Song.isEmpty() && filterList_Album.isEmpty())){
            songOfAlbumAdapter.setFilteredList(filterList_Song);
            adapter_albums.setFilteredList(filterList_Album);
        }



    }

}