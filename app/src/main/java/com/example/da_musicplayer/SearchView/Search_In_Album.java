package com.example.da_musicplayer.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.da_musicplayer.Adapter.SongOfAlbumAdapter;
import com.example.da_musicplayer.Album;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Player;
import com.example.da_musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class Search_In_Album extends AppCompatActivity {

    SearchView searchView_in_album;
    RecyclerView recyclerView_in_album;
    SongOfAlbumAdapter songOfAlbumAdapter;
    ArrayList<Songs_Item> songsItem_list;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_in_album);

        searchView_in_album = findViewById(R.id.searchView_in_album);
        searchView_in_album.clearFocus();
///////////////////////////////////////// Lấy dữ liệu và hiển thị danh sách ///////////////////////
        intent = getIntent();
        songsItem_list = (ArrayList<Songs_Item>) intent.getSerializableExtra("song_item_list");
        LinearLayoutManager layoutManager = new LinearLayoutManager(Search_In_Album.this, LinearLayoutManager.VERTICAL, false);
        recyclerView_in_album = findViewById(R.id.recyclerView_in_album);
        recyclerView_in_album.setLayoutManager(layoutManager);
        songOfAlbumAdapter = new SongOfAlbumAdapter(Search_In_Album.this, songsItem_list);
        recyclerView_in_album.setAdapter(songOfAlbumAdapter);
//////////////////////////////////////// Xử lý dữ liệu tìm kiếm
        searchView_in_album.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
///////////////////////////////////// Xử lý click ///////////////////////////
        songOfAlbumAdapter.setOnItemClickListener(new SongOfAlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Search_In_Album.this, Player.class);
                intent.putExtra("songs_album_list_fromSearchInAlbum", songsItem_list);
                intent.putExtra("position_song_fromSearchInAlbum", position);
                startActivity(intent);
            }
        });

    }
        private void filterList(String text){
            ArrayList<Songs_Item> filterList = new ArrayList<>();
            for (Songs_Item songsItem: songsItem_list){
                if(songsItem.getTitle_song().toLowerCase().contains(text.toLowerCase())){
                    filterList.add(songsItem);
                }
            }

            if (filterList.isEmpty()){
                Toast.makeText(this, "No song found", Toast.LENGTH_SHORT).show();
            } else {
                songOfAlbumAdapter.setFilteredList(filterList);
            }
        }
}