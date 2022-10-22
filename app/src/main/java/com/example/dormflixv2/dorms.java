package com.example.dormflixv2;

public class dorms {

    public String getDescription() {
        return description;
    }

    String dormName;
    String dormPlace;
    String dormPrice;
    int img;
    String description;


    public dorms(String dormName, String dormPlace, String dormPrice, int img, String description) {
        this.dormName = dormName;
        this.dormPlace = dormPlace;
        this.dormPrice = dormPrice;
        this.img = img;
        this.description = description;

    }

    public String getDormName() {
        return dormName;
    }

    public String getDormPlace() {
        return dormPlace;
    }

    public String getDormPrice() {
        return dormPrice;
    }

    public int getImg() {
        return img;
    }
}
