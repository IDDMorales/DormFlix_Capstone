package com.yotsutech.dormflix;

public class MainModel {
    String dormname,roomno,price,purl, desc,availbed;
    MainModel(){

    }


    public MainModel(String dormname, String room, String price, String purl, String desc,String availbed) {
        this.dormname = dormname;
        this.roomno = room;
        this.price = price;
        this.purl = purl;
        this.desc = desc;
        this.availbed = availbed;
    }


    public String getDormname() {
        return dormname;
    }

    public void setDormname(String dormname) {
        this.dormname = dormname;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvailbed() {
        return availbed;
    }

    public void setAvailbed(String availbed) {
        this.availbed = availbed;
    }
}
