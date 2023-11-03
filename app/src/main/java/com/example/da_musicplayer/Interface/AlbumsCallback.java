package com.example.da_musicplayer.Interface;

import com.example.da_musicplayer.Define.Albums;

import java.util.ArrayList;

public interface AlbumsCallback {
    void onAlbumsLoaded(ArrayList<Albums> albums);
    void onAlbumsLoadError(String errorMessage);
}
