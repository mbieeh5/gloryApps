package com.rraf.gloryservices.adaptor;

public class AbsenClass {
    String nama, jam, tanggal, imageUrl;
    public AbsenClass(){
        //kosongin
    }
    public AbsenClass(String nama, String jam, String tanggal, String imageUrl) {
        this.nama = nama;
        this.jam = jam;
        this.tanggal = tanggal;
        this.imageUrl = imageUrl;
    }
    public String getNama() {
        return nama;
    }
    public String getJam() {
        return jam;
    }
    public String getTanggal() {
        return tanggal;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
