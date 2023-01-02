package com.rraf.gloryservices.adaptor;

public class LeaderboardClass {
    String nama, point;

    public LeaderboardClass(){

    }

    public LeaderboardClass(String nama, String point) {
        this.nama = nama;
        this.point = point;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}