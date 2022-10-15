package com.example.dormflixv2;

public class dorms {

    String dormName;
    String dormPlace;
    String dormPrice;
    int img;


    public dorms(String dormName, String dormPlace, String dormPrice, int img) {
        this.dormName = dormName;
        this.dormPlace = dormPlace;
        this.dormPrice = dormPrice;
        this.img = img;
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
