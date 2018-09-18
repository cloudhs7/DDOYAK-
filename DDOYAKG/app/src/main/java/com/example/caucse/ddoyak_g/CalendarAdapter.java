package com.example.caucse.ddoyak_g;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {

    public static final String TAG = "CalendarMonthAdapter";

    Context mContext;

    private MonthItem[] items;
    private int countColumn = 7;

    int curYear;
    int curMonth;
    int firstDay;
    int lastDay;
    int startDay;

    Calendar mCalendar;

    public CalendarAdapter(Context context) {
        super();
        mContext = context;
        init();
    }

    public CalendarAdapter(Context context, AttributeSet attrs) {
        super();
        mContext = context;
        init();
    }

    private void init() {
        items = new MonthItem[7*6];

        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }

    private void recalculate() {
        mCalendar.set(Calendar.DAY_OF_MONTH,1);

        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);

        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        startDay = getFirstDayOfWeek();
    }

    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if(dayOfWeek == Calendar.SUNDAY){
            result = 0;
        }else if (dayOfWeek == Calendar.MONDAY){
            result = 1;
        }else if (dayOfWeek == Calendar.TUESDAY){
            result = 2;
        }else if (dayOfWeek == Calendar.WEDNESDAY){
            result = 3;
        }else if (dayOfWeek == Calendar.THURSDAY){
            result = 4;
        }else if (dayOfWeek == Calendar.FRIDAY){
            result = 5;
        }else if (dayOfWeek == Calendar.SATURDAY){
            result = 6;
        }

        return result;
    }

    private int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if(startDay == Calendar.SATURDAY){
            return Time.SATURDAY;
        } else if(startDay == Calendar.MONDAY){
            return Time.MONDAY;
        } else{
            return  Time.SUNDAY;
        }
    }

    private int getMonthLastDay(int curYear, int curMonth) {
        switch(curMonth){
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return(31);
            case 3:
            case 5:
            case 8:
            case 10:
                return(30);

            default:
                if(((curYear%4==0)&&(curYear%100!=0)) ||(curYear%400==0)){
                    return (29);
                }else{
                    return (28);
                }

        }
    }

    private void resetDayNumbers() {
        int set = 1;
        for(int i = 0; i<42;i++){
            int overNumber = 0;
            int dayNumber = (i+1) - firstDay;
            if(dayNumber < 1){
                if(curMonth-1 < 0){
                    overNumber = getMonthLastDay(curYear-1,12+curMonth)+dayNumber;
                } else {
                    overNumber = getMonthLastDay(curYear,curMonth-1)+dayNumber;
                }
                dayNumber = 0;
            } else if(dayNumber > lastDay){
                overNumber = dayNumber-lastDay;
                dayNumber = 0;
            }

            items[i] = new MonthItem(dayNumber,overNumber);
        }
    }

    @Override
    public int getCount() {
        return 7*6;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonthItemView itemView;
        if(convertView ==null){
            itemView = new MonthItemView(mContext);
        }else{
            itemView = (MonthItemView) convertView;
        }

        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,160);

        int rowIndex = position/countColumn;
        int columnIndex = position%countColumn;

        itemView.setItem(items[position]);
        itemView.setLayoutParams(params);
        itemView.setPadding(2,2,2,2);

        itemView.setGravity(Gravity.LEFT);
        if(columnIndex==0){
            itemView.setTextColor(Color.RED);
        } else if(columnIndex==6){
            itemView.setTextColor(Color.BLUE);
        } else{
            itemView.setTextColor(Color.BLACK);
        }

        if(items[position].getDay()==0){
            itemView.setBackgroundColor(Color.LTGRAY);
            itemView.setTextColor(Color.BLACK);
        } else{
            itemView.setBackgroundColor(Color.WHITE);
        }

        return itemView;
    }

    public void setPreviousMonth(){
        mCalendar.add(Calendar.MONTH,-1);
        recalculate();
        resetDayNumbers();
    }

    public void setNextMonth(){
        mCalendar.add(Calendar.MONTH,1);
        recalculate();
        resetDayNumbers();
    }

    public int getCurYear(){
        return curYear;
    }

    public int getCurMonth(){
        return curMonth;
    }
}