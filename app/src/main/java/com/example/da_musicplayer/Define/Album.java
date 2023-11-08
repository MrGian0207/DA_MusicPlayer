package com.example.da_musicplayer.Define;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private int id;
    private String source_photo;
    private String title_photo;
    private String key;
    private String name;
    private ArrayList<Songs_Item> songs;

    public Album(int id, String source_photo, String title_photo, String key, String name) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title_photo;
        this.key = key;
        this.name = name;
    }

    public Album(int id, String source_photo, String title_photo, String key, String name, ArrayList<Songs_Item> songs) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title_photo;
        this.key = key;
        this.name = name;
        this.songs = songs;
    }

    public ArrayList<Songs_Item> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Songs_Item> songs) {
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource_photo() {
        return source_photo;
    }

    public void setSource_photo(String source_photo) {
        this.source_photo = source_photo;
    }

    public String getTitle_photo() {
        return title_photo;
    }

    public void setTitle_photo(String title_photo) {
        this.title_photo = title_photo;
    }

}
