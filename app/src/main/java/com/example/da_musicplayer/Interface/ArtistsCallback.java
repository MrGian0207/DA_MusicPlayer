package com.example.da_musicplayer.Interface;

import com.example.da_musicplayer.Define.Artist;

import java.util.ArrayList;

public interface ArtistsCallback {
    void onArtistsLoaded(ArrayList<Artist> artists);
    void onArtistsLoadError(String errorMessage);
}