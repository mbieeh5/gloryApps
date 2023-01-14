package com.rraf.gloryservices.adaptor;

public class OutputRedeemClass {

    String gLink, gJudul, nama;
    Integer point;

    public OutputRedeemClass(){}

    public OutputRedeemClass(String gLink, String gJudul, String nama, Integer point) {
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
