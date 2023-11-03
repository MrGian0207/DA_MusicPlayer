package com.example.da_musicplayer.Data;


import androidx.annotation.NonNull;

import com.example.da_musicplayer.Define.Albums;
import com.example.da_musicplayer.Interface.AlbumsCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AlbumsData {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();


    public static void generateAlbumsData(final AlbumsCallback callback) {
        myRef.child("Albums").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Albums> albums = new ArrayList<>();
                if (snapshot.exists()){
                    for (DataSnapshot albumSnapshot : snapshot.getChildren()){
                          if (albumSnapshot.exists()){
                              Integer  id = 0;
                              String image = "";
                              String title = "";
                              String key = "";
                              String name = " ";
                              for(DataSnapshot albumSnapshot_item : albumSnapshot.getChildren()){

                                                if (albumSnapshot_item.getKey().equals("id")) {
                                                    id = albumSnapshot_item.getValue(Integer.class);
                                                }
                                                else if (albumSnapshot_item.getKey().equals("image")) {
                                                    image = albumSnapshot_item.getValue(String.class);
                                                }
                                                else if (albumSnapshot_item.getKey().equals("title")) {
                                                    title = albumSnapshot_item.getValue(String.class);
                                                }
                                                else if (albumSnapshot_item.getKey().equals("key")) {
                                                    key = albumSnapshot_item.getValue(String.class);
                                                }
                                                else if (albumSnapshot_item.getKey().equals("name")) {
                                                    name = albumSnapshot_item.getValue(String.class);
                                                }

                              }
                              Albums album = new Albums(id,image,title,key,name);
                              albums.add(album);
                         }
                    }
                }
                callback.onAlbumsLoaded(albums);
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onAlbumsLoadError("Lỗi khi tải dữ liệu: " + error.getMessage());
            }
        });
    }
}