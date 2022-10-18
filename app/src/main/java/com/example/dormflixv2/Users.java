package com.example.dormflixv2;

import android.widget.EditText;

public class Users {

    private String name;
    private String email;
    private String number;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Users(String name, String email, String number, String url) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.url = url;
    }
}