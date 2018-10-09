package com.example.caucse.ddoyak_g;

public class PatientInfo {
    String name;
    String phoneNumber;
    String id;

    public PatientInfo(String name, String phoneNumber,String id)
    {
        this.id = id;
        this.name = name;
        this.phoneNumber=phoneNumber;
    }

    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public  String getId(){ return id; }
}