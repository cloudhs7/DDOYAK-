package com.example.caucse.ddoyak;

public class Time {
    private int hour;
    private int min;

    public Time() {
    }

    public Time(int hour, int min){
        this.hour = hour;
        this.min = min;
    }

    public int getHour(){return hour;}
    public int getMin(){return min;}
}