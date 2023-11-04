package com.example.da_musicplayer.Data;

import androidx.annotation.NonNull;

import com.example.da_musicplayer.Define.Songs_Item;
import com.example.da_musicplayer.Interface.SongsItemCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SongsFavorite_Data {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();

    public static void generateSongsItem(final SongsItemCallback callback, String key) {
        myRef.child("Users/"+key+"/listSongFavourite").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Songs_Item> songs = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot artistSnapshot : snapshot.getChildren()) {
                        if (artistSnapshot.exists()) {
                            Integer id = 0;
                            String image = "";
                            String title = "";
                            String singer = "";
                            String linkSong = "";
                            for (DataSnapshot artistSnapshot_item : artistSnapshot.getChildren()) {
                                if (artistSnapshot_item.getKey().equals("id")) {
                                    id = artistSnapshot_item.getValue(Integer.class);
                                } else if (artistSnapshot_item.getKey().equals("source_photo")) {
                                    image = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("singer_song")) {
                                    singer = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("title_song")) {
                                    title = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("link_song")) {
                                    linkSong = artistSnapshot_item.getValue(String.class);
                                }

                            }
                            Songs_Item song = new Songs_Item(id, image, title, singer, linkSong);
                            songs.add(song);
                        }
                    }
                    callback.onSongsItemLoaded(songs);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                callback.onSongsItemLoadError("Lỗi khi tải dữ liệu: " + error.getMessage());
            }
        });
    }

}
