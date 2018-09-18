package com.example.caucse.ddoyak;

public class MonthItem {
    private int dayValue;
    private int overValue;
    private int checkingValue;//checkingvalue의 디폴트는 2로 놔서 아무 checking 도 안뜨게 한다. (해당 달의 날짜가 아니면)

    public MonthItem() {
    }

    public MonthItem(int dayValue, int overValue) {
        this.dayValue = dayValue;
        this.overValue = overValue;
        //this.checkingValue = checkingValue;
    }

    public int getDay() {
        return dayValue;
    }

    public int getOverValue() {
        return overValue;
    }

    public int getCheckingValue(){
        return checkingValue;
    }

    public void setCheckingValue(int checkingValue){
        this.checkingValue = checkingValue;
    }
    public void setDay(int dayValue) {
        this.dayValue = dayValue;
    }

    public void setOverValue(int overValue) {
        this.overValue = overValue;
    }

}
