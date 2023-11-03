package com.example.da_musicplayer.Define;

import java.io.Serializable;

public class Albums implements Serializable {
    private int id;
    private String source_photo;
    private String title_photo;
    private String key;
    private String name;

    public Albums(int id, String source_photo, String title_photo, String key, String name) {
        this.id = id;
        this.source_photo = source_photo;
        this.title_photo = title_photo;
        this.key = key;
        this.name = name;
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
