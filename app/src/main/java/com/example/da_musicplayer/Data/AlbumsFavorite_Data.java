package com.example.da_musicplayer.Data;

import androidx.annotation.NonNull;

import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlbumsFavorite_Data {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();

    public static void generateAlbumFavouriteItem(final AlbumsCallback callback, String key) {
        myRef.child("Users/"+key+"/listAlbumFavourite").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Albums> albums = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot artistSnapshot : snapshot.getChildren()) {
                        if (artistSnapshot.exists()) {
                            Integer id = 0;
                            String key = "";
                            String image = "";
                            String title = "";
                            String name = "";
                            ArrayList<Songs_Item> listSong = new ArrayList<>();

                            for (DataSnapshot artistSnapshot_item : artistSnapshot.getChildren()) {
                                if (artistSnapshot_item.getKey().equals("id")) {
                                    id = artistSnapshot_item.getValue(Integer.class);
                                } else if (artistSnapshot_item.getKey().equals("key")) {
                                    key = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("name")) {
                                    name = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("source_photo")) {
                                    image = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("title_photo")) {
                                    title = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("songs")) {
                                    for(DataSnapshot song_item: artistSnapshot_item.getChildren()){
                                            listSong.add(song_item.getValue(Songs_Item.class));
                                    }
                                }

                            }
                            Albums album = new Albums(id, image, title, key, name, listSong);
                            albums.add(album);
                        }
                    }
                    callback.onAlbumsLoaded(albums);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                callback.onAlbumsLoadError("Lỗi khi tải dữ liệu: " + error.getMessage());
            }
        });
    }

}
