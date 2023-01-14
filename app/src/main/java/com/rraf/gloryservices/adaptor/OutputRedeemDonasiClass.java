package com.rraf.gloryservices.adaptor;

public class OutputRedeemDonasiClass {

    String gLink, gJudul, nama;
    Integer point;

    public OutputRedeemDonasiClass(){}

    public OutputRedeemDonasiClass(String gLink, String gJudul, String nama, Integer point) {
        this.gLink = gLink;
        this.gJudul = gJudul;
        this.nama = nama;
        this.point = point;
    }

    public Integer getPoint(){
        return point;
    }
    public String getgJudul() {
        return gJudul;
    }
    public String getNama(){
        return nama;
    }
    public String getgLink() {
        return gLink;
    }
}
