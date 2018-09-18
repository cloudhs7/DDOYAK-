package com.example.caucse.ddoyak;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class GridAdapter extends BaseAdapter {

    Context context;
    LinearLayout linear1;   // 복용 circle, 미복용 triangle 표시하기 위한 레이아웃
    TextView tv;    //일 표시 tv
    String packName;

    String medicine_taken = "takencircle";
    String medicine_not_taken = "nottaken";
    public int taken_resID;
    public int nottaken_resID;

    int layout;
    LayoutInflater inflater;

    private MonthItem[] items;
    private int countColumn = 7;

    int curYear;
    int curMonth;
    int firstDay;
    int lastDay;
    int startDay;

    Calendar calendar;
    private int selectedPosition = -1;  //selectedPosition 초기화

    public GridAdapter(Context context) {
        super();
        this.context = context;
        init();
    }

    public GridAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();

    }

    //MonthItem 42칸 초기화
    private void init() {
        items = new MonthItem[7 * 6];

        calendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }

    //해당 날짜의 연,월,일, 해당 달의 첫째날, 마지막날 지정
    private void recalculate() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);

        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        startDay = getFirstDayOfWeek();
    }

    //해당 월의 첫째 날에 따라 result값(0~6) 지정
    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }

        return result;
    }

    private int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    //월에 따른 말일 지정(31일, 30일)
    private int getMonthLastDay(int curYear, int curMonth) {
        switch (curMonth) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);
            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                //윤년에 따른 4월의 말일 계산
                if (((curYear % 4 == 0) && (curYear % 100 != 0)) || (curYear % 400 == 0)) {
                    return (29);
                } else {
                    return (28);
                }

        }
    }

    //해당 달의 날짜는 dayNumber로, 전&후 달의 날짜는 overNumber로 지정
    private void resetDayNumbers() {
        int set = 1;
        for (int i = 0; i < 42; i++) {
            int overNumber = 0;
            int dayNumber = (i + 1) - firstDay;
            if (dayNumber < 1) {
                if (curMonth - 1 < 0) {
                    overNumber = getMonthLastDay(curYear - 1, 12 + curMonth) + dayNumber;
                } else {
                    overNumber = getMonthLastDay(curYear, curMonth - 1) + dayNumber;
                }
                dayNumber = 0;
            } else if (dayNumber > lastDay) {
                overNumber = dayNumber - lastDay;
                dayNumber = 0;
            }
            items[i] = new MonthItem(dayNumber, overNumber);

        }
    }

    @Override
    public int getCount() {
        return 7 * 6;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItem(MonthItem item) {
        int day = item.getDay();
        if (day != 0) {
            tv.setText(String.valueOf(day));
        } else {
            tv.setText(String.valueOf(item.getOverValue()));
        }
    }

    //복용(circle) 미복용(triangle) 여부를 날마다 그림으로 표시
    public void UpdateImageonCalendar(int position) {
        MonthItem item = (MonthItem) CalendarMainActivity.gridAdapter.getItem(position);
        int curDay = item.getDay();
        //drawble로부터 circle, triangle 이미지 얻어오기
        taken_resID = context.getResources().getIdentifier(medicine_taken, "drawable", packName);
        nottaken_resID = context.getResources().getIdentifier(medicine_not_taken, "drawable", packName);
        packName = context.getPackageName();

        //set circle image
        ImageView takenimage = new ImageView(context);
        takenimage.setImageResource(taken_resID);
        takenimage.setAdjustViewBounds(true);

        //set triangle image
        ImageView nottakenimage = new ImageView(context);
        nottakenimage.setImageResource(nottaken_resID);
        nottakenimage.setAdjustViewBounds(true);

        linear1.removeAllViews();

        for (int i = 0; i < CalendarMainActivity.data.size(); i++) {
            //해당 날짜의 복용&미복용 데이터 얻어오기
            if ((Integer.parseInt(CalendarMainActivity.data.get(i).getYear()) == curYear)
                    && (Integer.parseInt(CalendarMainActivity.data.get(i).getMonth()) == (curMonth) + 1)
                    && (Integer.parseInt(CalendarMainActivity.data.get(i).getDay()) == curDay)) {
                if (Integer.parseInt(CalendarMainActivity.data.get(i).getCheck()) == 1){
                    //checkingValue가 1이면 linear1.addView를 통한 circle 이미지 띄우기
                    item.setCheckingValue(1);//복용
                    linear1.addView(takenimage, 25, 25);}
                else if (Integer.parseInt(CalendarMainActivity.data.get(i).getCheck()) == 0){
                    //checkingValue가 0이면 linear1.addView를 통한 triangle 이미지 띄우기
                    item.setCheckingValue(0);//미복용
                    linear1.addView(nottakenimage, 25, 25);}
                else
                    item.setCheckingValue(2);
            }
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //view가 최종적으로 띄울 MonthItemView
        if (view == null) {
            view = inflater.inflate(layout, null);
        }

        tv = (TextView) view.findViewById(R.id.day_num);    //몇일인지 띄울 textview
        linear1 = (LinearLayout) view.findViewById(R.id.linear1);   //복용 미복용 이미지 띄울 linearlayout

        int columnIndex = position % countColumn;

        setItem(items[position]);   //일별 날짜 및 색 지정
        UpdateImageonCalendar(position);    // 복용 현황 이미지들 지정

        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 160);
        view.setLayoutParams(params);
        view.setPadding(2, 2, 2, 2);

        tv.setGravity(Gravity.LEFT);
        //요일에 따른 색 지정
        if (columnIndex == 0) {
            tv.setTextColor(Color.RED);
        } else if (columnIndex == 6) {
            tv.setTextColor(Color.BLUE);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        //클릭했을 때 색 지정
        if (position == getSelectedPosition()) {
            view.setBackgroundColor(Color.rgb(242, 203, 97));
        } else if (items[position].getDay() == 0) {
            view.setBackgroundColor(Color.rgb(238, 229, 222));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    //이전 달 클릭했을 시
    public void setPreviousMonth() {
        calendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    //다음 달 클릭했을 시
    public void setNextMonth() {
        calendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }


}

