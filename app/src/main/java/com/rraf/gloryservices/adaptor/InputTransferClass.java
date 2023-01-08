package com.rraf.gloryservices.adaptor;

public class InputTransferClass {
    String iTgl, iBank, iNama,iNorek, iTfMana, Id;
    Integer iNom;

    public InputTransferClass() {
    }

    public InputTransferClass(String Id, String iTgl,String iBank, Integer iNom, String iNorek, String iNama, String iTfMana) {
        this.Id = Id;
        this.iTgl = iTgl;
        this.iBank = iBank;
        this.iNom = iNom;
        this.iNorek = iNorek;
        this.iNama = iNama;
        this.iTfMana = iTfMana;
    }

    public String getiBank() {
        return iBank;
    }
    public void setiBank(String iBank) {
        this.iBank = iBank;
    }
    public Integer getiNom() {
        return iNom;
    }
    public void setiNom(Integer iNom) {
        this.iNom = iNom;
    }
    public String getiNorek() {
        return iNorek;
    }
    public void setiNorek(String iNorek) {
        this.iNorek = iNorek;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getiTfMana(){
        return iTfMana;
    }
    public void setiTfMana(){
        this.iTfMana = iTfMana;
    }
    public String getiTgl() {
        return iTgl;
    }
    public void setiTgl(String iTgl) {
        this.iTgl = iTgl;
    }
    public String getiNama() {
        return iNama;
    }
    public void setiNama(String iHp) {
        this.iNama = iNama;
    }

}
