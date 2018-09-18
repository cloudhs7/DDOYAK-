package com.example.caucse.ddoyak_g;

public class PatientInfo {
    String name;
    String phoneNumber;

    public PatientInfo(String name, String phoneNumber)
    {
        this.name = name;
        this.phoneNumber=phoneNumber;
    }

    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
}