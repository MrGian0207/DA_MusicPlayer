package com.example.da_musicplayer.Data;

import androidx.annotation.NonNull;

import com.example.da_musicplayer.Define.Artists;
import com.example.da_musicplayer.Interface.ArtistsCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArtistsData {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();


    public static void generateArtist(final ArtistsCallback callback) {
        myRef.child("Artists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Artists> artists = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot artistSnapshot : snapshot.getChildren()) {
                        if (artistSnapshot.exists()) {
                            Integer id = 0;
                            String image = "";
                            String title = "";
                            for (DataSnapshot artistSnapshot_item : artistSnapshot.getChildren()) {

                                if (artistSnapshot_item.getKey().equals("id")) {
                                    id = artistSnapshot_item.getValue(Integer.class);
                                } else if (artistSnapshot_item.getKey().equals("image")) {
                                    image = artistSnapshot_item.getValue(String.class);
                                } else if (artistSnapshot_item.getKey().equals("title")) {
                                    title = artistSnapshot_item.getValue(String.class);
                                }

                            }
                            Artists artist = new Artists(id, image, title);
                            artists.add(artist);
                        }
                    }
                    callback.onArtistsLoaded(artists);
                }
            }
                @Override
                public void onCancelled (@NonNull DatabaseError error){
                    callback.onArtistsLoadError("Lỗi khi tải dữ liệu: " + error.getMessage());
                }
        });
    }


//    public static void getPhotoFromId(int id, final AlbumCallback callback) {
//        generatePhotoData(new AlbumsCallback() {
//            @Override
//            public void onAlbumsLoaded(ArrayList<Albums> albums) {
//                for (Albums album : albums) {
//                    if (album.getId() == id) {
//                        callback.onAlbumLoaded(album);
//                        return; // Tìm thấy album, không cần kiểm tra tiếp
//                    }
//                }
//                callback.onAlbumNotFound(); // Không tìm thấy album với id đã cho
//            }
//
//            @Override
//            public void onAlbumsLoadError(String errorMessage) {
//                callback.onAlbumLoadError(errorMessage); // Xử lý lỗi khi tải dữ liệu
//            }
//        });
//    }
}
