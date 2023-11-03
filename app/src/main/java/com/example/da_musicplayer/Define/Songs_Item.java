package com.example.da_musicplayer.Define;

import java.io.Serializable;

public class Songs_Item implements Serializable {
    private int id;
    private String source_photo;
    private String title_song;
    private String singer_song;
    private String link_song;

    public Songs_Item(int id, String source_photo, String title_song, String singer_song, String link_song) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_song = title_song;
        this.singer_song = singer_song;
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

    public String getTitle_song() {
        return title_song;
    }

    public void setTitle_song(String title_song) {
        this.title_song = title_song;
    }

    public String getSinger_song() {
        return singer_song;
    }

    public void setSinger_song(String singer_song) {
        this.singer_song = singer_song;
    }

    public String getLink_song() {
        return link_song;
    }

    public void setLink_song(String link_song) {
        this.link_song = link_song;
    }
}
