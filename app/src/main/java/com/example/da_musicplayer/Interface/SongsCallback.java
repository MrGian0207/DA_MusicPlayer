package com.example.da_musicplayer.Interface;


import com.example.da_musicplayer.Define.Songs;

import java.util.ArrayList;

public interface SongsCallback {
    void onSongsLoaded(ArrayList<Songs> artists);
    void onSongsLoadError(String errorMessage);
}