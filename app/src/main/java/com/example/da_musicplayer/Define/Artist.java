package com.example.da_musicplayer.Define;

import java.io.Serializable;
import java.util.ArrayList;

public class Artist implements Serializable {
    private int id;
    private String source_photo;
    private String title_photo;
    private String key;
    private ArrayList<Songs_Item> listSong;

    public Artist(int id, String source_photo, String title) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title;
    }
    public Artist(int id, String source_photo, String title, String key, ArrayList<Songs_Item> listSong) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title;
        this.key = key;
        this.listSong = listSong;
    }

    public Artist(int id, String source_photo, String title_photo, String key) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title_photo;
        this.key = key;
    }

    public ArrayList<Songs_Item> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<Songs_Item> listSong) {
        this.listSong = listSong;
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
