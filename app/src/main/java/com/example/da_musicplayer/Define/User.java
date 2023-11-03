package com.example.da_musicplayer.Define;

public class User {
    private String email;
    private String Uid;


    public User (){

    }

    public User(String email, String uid) {
        this.email = email;
        this.Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
