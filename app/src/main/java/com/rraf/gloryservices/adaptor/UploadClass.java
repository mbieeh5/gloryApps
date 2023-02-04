package com.rraf.gloryservices.adaptor;

public class UploadClass {
    private String mName, mImageUrl, tanggal, jam;
    public UploadClass(){
        //empty constructor needed
    }
    public UploadClass(String nama, String imageUrl, String tanggal, String jam){
       if(nama.trim().equals("")){
           nama = "Tanpa Nama";
       }
       mName = nama;
       this. mImageUrl = imageUrl;
       this.tanggal = tanggal;
       this.jam = jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return mName;
    }

    public void setName(String nama) {
        mName = nama;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        mImageUrl = ImageUrl;
    }
}
