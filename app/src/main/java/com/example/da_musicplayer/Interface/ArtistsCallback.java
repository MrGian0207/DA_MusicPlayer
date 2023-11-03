package com.example.da_musicplayer.Interface;

import com.example.da_musicplayer.Define.Artists;

import java.util.ArrayList;

public interface ArtistsCallback {
    void onArtistsLoaded(ArrayList<Artists> artists);
    void onArtistsLoadError(String errorMessage);
}