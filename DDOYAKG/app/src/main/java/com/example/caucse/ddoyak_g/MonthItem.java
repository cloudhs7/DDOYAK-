package com.example.caucse.ddoyak_g;


public class MonthItem {
    private int dayValue;
    private int overValue;

    public MonthItem(){ }

    public MonthItem(int dayValue, int overValue) {
        this.dayValue = dayValue;
        this.overValue= overValue;
    }

    public int getDay(){
        return dayValue;
    }

    public int getOverValue(){
        return overValue;
    }

    public void setDay(int dayValue){
        this.dayValue = dayValue;
    }

    public void setOverValue(int overValue){
        this.overValue = overValue;
    }
}