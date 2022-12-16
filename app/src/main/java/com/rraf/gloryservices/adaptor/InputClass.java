package com.rraf.gloryservices.adaptor;

public class InputClass {
    String iTgl, iHp, iRsk, iHrg, iPenerima;

    public InputClass() {
    }

    public InputClass(String iTgl,String iPenerima, String iHp, String iRsk, String iHrg) {
        this.iTgl = iTgl;
        this.iPenerima = iPenerima;
        this.iHp = iHp;
        this.iRsk = iRsk;
        this.iHrg = iHrg;
    }

    public String getiPenerima(){
        return iPenerima;
    }

    public void setiPenerima(){
        this.iPenerima = iPenerima;
    }

    public String getiTgl() {
        return iTgl;
    }

    public void setiTgl(String iTgl) {
        this.iTgl = iTgl;
    }

    public String getiHp() {
        return iHp;
    }

    public void setiHp(String iHp) {
        this.iHp = iHp;
    }

    public String getiRsk() {
        return iRsk;
    }

    public void setiRsk(String iRsk) {
        this.iRsk = iRsk;
    }

    public String getiHrg() {
        return iHrg;
    }

    public void setiHrg(String iHrg) {
        this.iHrg = iHrg;
    }
}
