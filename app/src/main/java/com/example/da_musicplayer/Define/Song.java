package com.example.da_musicplayer.Define;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String source_photo;
    private String title_photo;
    private String link_song;

    public Song(int id, String source_photo, String title_photo, String link_song) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title_photo;
        this.link_song = link_song;
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

    public String getLink_song() {
        return link_song;
    }

    public void setLink_song(String link_song) {
        this.link_song = link_song;
    }
}