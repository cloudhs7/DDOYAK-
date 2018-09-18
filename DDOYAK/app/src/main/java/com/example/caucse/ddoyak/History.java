package com.example.caucse.ddoyak;

//복용 여부를 판단하는 data 형식 class
public class History {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String min;
    private String check;
    private String name;

    public History(String year, String month, String day, String hour, String min, String check, String name){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.check = check;
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDays(){
        return (year+"년 "+month+"월 "+day+"일");
    }

    public String getTime(){
        String time = hour+"시 "+min+"분";
        return time;
    }

    public String getCheck() {
        return check;
    }

    public String getName() {
        return name;
    }
}