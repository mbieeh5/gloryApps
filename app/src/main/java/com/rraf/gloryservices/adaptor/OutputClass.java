package com.rraf.gloryservices.adaptor;

import android.view.ViewGroup;

public class OutputClass {

    String iHp, iRsk, iTgl, iHrg, iPenerima, iStatus, id;
    public OutputClass(){

    }
    public OutputClass(String id, String iTgl, String iHp, String iRsk, String iHrg, String iPenerima,String iStatus) {
            this.id = id;
            this.iTgl = iTgl;
            this.iHp = iHp;
            this.iRsk = iRsk;
            this.iHrg = iHrg;
            this.iPenerima = iPenerima;
            this.iStatus = iStatus;

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
