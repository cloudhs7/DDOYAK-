package com.example.caucse.ddoyak;

public class ScheduleInfo {
    String date, startTime, endTime, schedule;

    public ScheduleInfo(String date, String startTime, String endTime, String schedule){
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.schedule=schedule;
    }

    public String getSchedule(){
        return schedule;
    }
    public String getDate() {return date;}
    public String getStartTime() {return startTime;}
    public String getEndTime() {return endTime;}
}