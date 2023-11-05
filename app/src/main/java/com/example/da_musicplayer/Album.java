package com.example.da_musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.da_musicplayer.Adapter.SongOfAlbumAdapter;

import com.example.da_musicplayer.Data.Songs_Item_Data;
import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Fragment.SearchFragment;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.example.da_musicplayer.SearchView.Search_In_Album;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Album extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Songs_Item> songs_of_album_list;
    SongOfAlbumAdapter songOfAlbumAdapter;
    CardView search_in_album;
    Intent intent;
    ImageView image_album;
    LinearLayout backFromAlbumActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        image_album = findViewById(R.id.image_album);
        search_in_album = findViewById(R.id.search_in_album);
        backFromAlbumActivity = findViewById(R.id.backFromAlbumActivity);

        backFromAlbumActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                boolean top_albumClicked = intent.getBooleanExtra("top_albumClicked",false);
                boolean album_searchFragmentClicked = intent.getBooleanExtra("album_searchFragmentClicked", false);
                if (top_albumClicked == true ) {
//                    Intent intentBack = new Intent(Album.this, MainActivity.class);
//                    startActivity(intentBack);
                    onBackPressed(Album.this);
                }
                else if(album_searchFragmentClicked == true) {
                        onBackPressed(Album.this);
                }

            }
        });



        intent = getIntent();
        Albums album = (Albums) intent.getSerializableExtra("album");
        Picasso.get().load(album.getSource_photo())
                .resize(2000, 2000)
                .centerCrop()
                .into(image_album);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        search_in_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_of_searchInAlbum = new Intent(Album.this, Search_In_Album.class);

                Songs_Item_Data.generateSongsItem(new SongsItemCallback() {
                    @Override
                    public void onSongsItemLoaded(ArrayList<Songs_Item> Songs_Item_List) {
                        intent_of_searchInAlbum.putExtra("song_item_list", Songs_Item_List);
                        startActivity(intent_of_searchInAlbum);
                    }

                    @Override
                    public void onSongsItemLoadError(String errorMessage) {
                        System.out.println("Lỗi: " + errorMessage);
                    }
                },album.getKey());

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Songs_Item_Data.generateSongsItem(new SongsItemCallback(){
            @Override
            public void onSongsItemLoaded(ArrayList<Songs_Item> Songs_Item_List) {
                songs_of_album_list = Songs_Item_List;
                LinearLayoutManager layoutManager = new LinearLayoutManager(Album.this, LinearLayoutManager.VERTICAL, false);
                recyclerView = findViewById(R.id.songs_item_recyclerview);
                recyclerView.setLayoutManager(layoutManager);
                songOfAlbumAdapter = new SongOfAlbumAdapter(Album.this, songs_of_album_list);
                recyclerView.setAdapter(songOfAlbumAdapter);
                songOfAlbumAdapter.setOnItemClickListener(new SongOfAlbumAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(Album.this, Player.class);
                        intent.putExtra("songs_album_list", songs_of_album_list);
                        intent.putExtra("position_song", position);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onSongsItemLoadError(String errorMessage) {
                // Xử lý lỗi ở đây
                System.out.println("Lỗi: " + errorMessage);
            }
        },album.getKey());
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

