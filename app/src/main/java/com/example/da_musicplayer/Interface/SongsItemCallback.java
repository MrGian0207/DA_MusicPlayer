package com.example.da_musicplayer.Interface;

import com.example.da_musicplayer.Define.Songs_Item;

import java.util.ArrayList;

public interface SongsItemCallback {
    void onSongsItemLoaded(ArrayList<Songs_Item> artists);
    void onSongsItemLoadError(String errorMessage);
}
