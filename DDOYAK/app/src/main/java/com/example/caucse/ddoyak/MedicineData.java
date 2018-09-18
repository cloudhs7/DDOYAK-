package com.example.caucse.ddoyak;

public class MedicineData {

    private String name;
    private String stime;
    private String etime;

    public MedicineData(String name, String stime, String etime){
        this.name=name;
        this.stime=stime;
        this.etime=etime;
    }

    public String getName() {
        return name;
    }

    public String getStime() {
        return stime;
    }

    public String getEtime() {
        return etime;
    }
}