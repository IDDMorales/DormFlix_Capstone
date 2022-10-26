package com.example.dormflixv2;

public class uBook {
    String fullName;
    String pNumber;
    String roomno;
    String date;
    String purl;
    String dornname;

    public uBook(String fullName, String pNumber, String roomno, String date, String dornname, String purl) {
     this.fullName = fullName;
     this.pNumber = pNumber;
     this.roomno = roomno;
     this.date= date;
     this.dornname = dornname;
     this.purl=purl;



    }
    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getDornname() {
        return dornname;
    }

    public void setDornname(String dornname) {
        this.dornname = dornname;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
