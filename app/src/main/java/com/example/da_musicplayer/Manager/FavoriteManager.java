package com.example.da_musicplayer.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.da_musicplayer.MainActivity;

public class FavoriteManager {
    private SharedPreferences sharedPreferences;
    private Context context;

    public FavoriteManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("FavoriteAlbums"+ MainActivity.uid_User(), Context.MODE_PRIVATE);
    }

    public boolean isFavorite(String albumKey) {
        return sharedPreferences.getBoolean(albumKey, false);
    }

    public void setFavorite(String albumKey, boolean isFavorite) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(albumKey, isFavorite);
        editor.apply();
    }
}

