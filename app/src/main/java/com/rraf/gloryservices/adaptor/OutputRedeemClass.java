package com.rraf.gloryservices.adaptor;

public class OutputRedeemClass {

    String gLink, gJudul, nama, statusR;
    Integer point;

    public OutputRedeemClass(){}

    public OutputRedeemClass(String gLink, String gJudul, String nama, String statusR, Integer point) {
        this.gLink = gLink;
        this.gJudul = gJudul;
        this.nama = nama;
        this.statusR = statusR;
        this.point = point;
    }

    public Integer getPoint(){
        return point;
    }
    public String getStatusR() {
        return statusR;
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
