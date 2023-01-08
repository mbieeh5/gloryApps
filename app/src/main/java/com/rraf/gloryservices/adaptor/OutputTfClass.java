package com.rraf.gloryservices.adaptor;

public class OutputTfClass {

    String iNama, iTgl,iBank, iNorek, iTfMana, iStatus, id;
    Integer iNom;
    public OutputTfClass(){

    }

    public OutputTfClass(String iNama, String Id, Integer iNom, String iBank, String iStatus, String iNorek, String iTgl, String iTfMana) {
        this.iNama = iNama;
        this.iTgl = iTgl;
        this.iBank = iBank;
        this.iNorek = iNorek;
        this.iTfMana = iTfMana;
        this.iStatus = iStatus;
        this.id = id;
        this.iNom = iNom;
    }

    public String getiBank() {
        return iBank;
    }

    public void setiBank(String iBank) {
        this.iBank = iBank;
    }

    public String getiNama() {
        return iNama;
    }

    public void setiNama(String iNama) {
        this.iNama = iNama;
    }

    public String getiTgl() {
        return iTgl;
    }

    public void setiTgl(String iTgl) {
        this.iTgl = iTgl;
    }

    public String getiNorek() {
        return iNorek;
    }

    public void setiNorek(String iNorek) {
        this.iNorek = iNorek;
    }

    public String getiTfMana() {
        return iTfMana;
    }

    public void setiTfMana(String iTfMana) {
        this.iTfMana = iTfMana;
    }

    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getiNom() {
        return iNom;
    }

    public void setiNom(Integer iNom) {
        this.iNom = iNom;
    }
}
