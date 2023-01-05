package com.rraf.gloryservices.adaptor;

public class LeaderboardClass {
    String nama;
    Integer point;
    public LeaderboardClass(){

    }

    public LeaderboardClass(String nama, Integer point) {
        this.nama = nama;
        this.point = point;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}