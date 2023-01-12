package com.rraf.gloryservices.adaptor;

import android.view.ViewGroup;

public class OutputClass {

    String iHp, iRsk, iTgl, iMdl, iHrg, iPenerima, iStatus, id, iKerjaan, iTglK;

    public OutputClass(){

    }

    public OutputClass(String id, String iTgl, String iTglK, String iHp, String iRsk, String iMdl, String iHrg, String iPenerima,String iStatus, String iKerjaan) {
            this.id = id;
            this.iTgl = iTgl;
            this.iTglK = iTglK;
            this.iMdl = iMdl;
            this.iHp = iHp;
            this.iRsk = iRsk;
            this.iHrg = iHrg;
            this.iPenerima = iPenerima;
            this.iStatus = iStatus;
            this.iKerjaan = iKerjaan;
    }

    public void setiHp(String iHp) {
        this.iHp = iHp;
    }

    public void setiRsk(String iRsk) {
        this.iRsk = iRsk;
    }

    public void setiTgl(String iTgl) {
        this.iTgl = iTgl;
    }

    public void setiMdl(String iMdl) {
        this.iMdl = iMdl;
    }

    public void setiHrg(String iHrg) {
        this.iHrg = iHrg;
    }

    public void setiPenerima(String iPenerima) {
        this.iPenerima = iPenerima;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setiKerjaan(String iKerjaan) {
        this.iKerjaan = iKerjaan;
    }

    public void setiTglK(String iTglK) {
        this.iTglK = iTglK;
    }

    public String getiTglK(){
        return iTglK;
    }

    public String getiMdl(){
        return iMdl;
    }

    public String getiKerjaan(){
        return iKerjaan;
    }

    public String getiPenerima() {
        return iPenerima;
    }

    public String getiStatus() {
        return iStatus;
    }

    public String getId() {
        return id;
    }

    public String getiHp() {
        return iHp;
    }

    public String getiRsk() {
        return iRsk;
    }

    public String getiTgl() {
        return iTgl;
    }

    public String getiHrg() {
        return iHrg;
    }
}
