package com.rraf.gloryservices.adaptor;

public class OutputRedeemHistoryClass {

    String nama, hTgl, hTujuan, hRedeemKe, sn, status;
    Integer point, hJmlRedeem;

    public OutputRedeemHistoryClass(){}

    public OutputRedeemHistoryClass(String nama, String hTgl, String hTujuan, Integer hJmlRedeem, String hRedeemKe, String status, String sn, Integer point) {
        this.nama = nama;
        this.hTgl = hTgl;
        this.hTujuan = hTujuan;
        this.hJmlRedeem = hJmlRedeem;
        this.hRedeemKe = hRedeemKe;
        this.status = status;
        this.sn = sn;
        this.point = point;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String gethTgl() {
        return hTgl;
    }
    public void sethTgl(String hTgl) {
        this.hTgl = hTgl;
    }
    public String gethTujuan() {
        return hTujuan;
    }
    public void sethTujuan(String hTujuan) {
        this.hTujuan = hTujuan;
    }
    public Integer gethJmlRedeem() {
        return hJmlRedeem;
    }
    public void sethJmlRedeem(Integer hJmlRedeem) {
        this.hJmlRedeem = hJmlRedeem;
    }
    public String gethRedeemKe() {
        return hRedeemKe;
    }
    public void sethRedeemKe(String hRedeemKe) {
        this.hRedeemKe = hRedeemKe;
    }
    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
}
