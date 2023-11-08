package com.example.da_musicplayer.Interface;

import com.example.da_musicplayer.Define.Album;

import java.util.ArrayList;

public interface AlbumsCallback {
    void onAlbumsLoaded(ArrayList<Album> albums);
    void onAlbumsLoadError(String errorMessage);
}
