package com.example.da_musicplayer.Interface;


import com.example.da_musicplayer.Define.Song;

import java.util.ArrayList;

public interface SongsCallback {
    void onSongsLoaded(ArrayList<Song> artists);
    void onSongsLoadError(String errorMessage);
}