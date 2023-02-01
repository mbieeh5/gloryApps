package com.rraf.gloryservices.adaptor;

public class AdminInputClass {
    String nama;
    Integer point;
    boolean pointAdded;

    public AdminInputClass(String nama, Integer point, boolean pointAdded) {
        this.nama = nama;
        this.point = point;
        this.pointAdded = pointAdded;
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
    public boolean isPointAdded() {
        return pointAdded;
    }
    public void setPointAdded(boolean pointAdded) {
        this.pointAdded = pointAdded;
    }
}
