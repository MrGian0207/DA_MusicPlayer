package com.example.da_musicplayer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.da_musicplayer.Adapter.SongsOfArtistAdapter;
import com.example.da_musicplayer.Data.SongsOfTopArtist_Data;
import com.example.da_musicplayer.Define.Artist;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.example.da_musicplayer.SearchView.Search_In_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistsActivity extends AppCompatActivity {
    LinearLayout backFromAritstActivity;
    CardView search_in_artist;
    ImageView image_artist;
    RecyclerView songs_of_artist_recyclerview;
    Intent intent;
    ArrayList<Songs_Item> songs_of_top_artist_list;
    SongsOfArtistAdapter songsOfArtistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
            backFromAritstActivity = findViewById(R.id.backFromAritstActivity);
            search_in_artist = findViewById(R.id.search_in_artist);
            image_artist = findViewById(R.id.image_artist);
            songs_of_artist_recyclerview = findViewById(R.id.songs_of_artist_recyclerview);
            backFromAritstActivity = findViewById(R.id.backFromAritstActivity);

            backFromAritstActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                boolean top_artistClicked = intent.getBooleanExtra("top_artistClicked",false);
                boolean album_searchFragmentClicked = intent.getBooleanExtra("album_searchFragmentClicked", false);
                if (top_artistClicked == true ) {
                    onBackPressed(ArtistsActivity.this);
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
            intent = getIntent();
            Artist artist = (Artist) intent.getSerializableExtra("artist");
            Picasso.get().load(artist.getSource_photo())
                .resize(2000, 2000)
                .centerCrop()
                .into(image_artist);
////////////////////////////////////////////////////////////////////////////////////////////////////
        search_in_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_of_searchInArtist = new Intent(ArtistsActivity.this, Search_In_Activity.class);
                SongsOfTopArtist_Data.generateSongsItemOfTopArtist(new SongsItemCallback() {
                    @Override
                    public void onSongsItemLoaded(ArrayList<Songs_Item> Songs_Item_List) {
                        intent_of_searchInArtist.putExtra("song_item_list", Songs_Item_List);
                        startActivity(intent_of_searchInArtist);
                    }

                    @Override
                    public void onSongsItemLoadError(String errorMessage) {
                        System.out.println("Lỗi: " + errorMessage);
                    }
                },artist.getKey());
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////
        SongsOfTopArtist_Data.generateSongsItemOfTopArtist(new SongsItemCallback() {
            @Override
            public void onSongsItemLoaded(ArrayList<Songs_Item> songsTopArtistList) {
                songs_of_top_artist_list = songsTopArtistList;
                LinearLayoutManager layoutManager = new LinearLayoutManager(ArtistsActivity.this, LinearLayoutManager.VERTICAL, false);
                songs_of_artist_recyclerview = findViewById(R.id.songs_of_artist_recyclerview);
                songs_of_artist_recyclerview.setLayoutManager(layoutManager);
                songsOfArtistAdapter = new SongsOfArtistAdapter(ArtistsActivity.this, songsTopArtistList);
                songs_of_artist_recyclerview.setAdapter(songsOfArtistAdapter);
                songsOfArtistAdapter.setOnItemClickListener(new SongsOfArtistAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(ArtistsActivity.this, PlayerActivity.class);
                        intent.putExtra("songs_artist_list", songs_of_top_artist_list);
                        intent.putExtra("position_Artist",position);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onSongsItemLoadError(String errorMessage) {
            }
        },artist.getKey());
////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    @SuppressWarnings("deprecation")
    // Trong hoạt động mới của bạn
    public void onBackPressed(Context context) {
        super.onBackPressed();
        // Gọi phương thức navigateToSearchFragment() để quay lại SearchFragment
        MainActivity mainActivity = new MainActivity(); // Tạo một đối tượng MainActivity
        mainActivity.navigateToFragment(context); // Gọi phương thức để quay lại SearchFragment
    }
}