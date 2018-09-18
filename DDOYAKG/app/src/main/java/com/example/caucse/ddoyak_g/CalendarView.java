package com.example.caucse.ddoyak_g;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class CalendarView extends GridView {

    CalendarAdapter adapter;

    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundColor(Color.DKGRAY);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setNumColumns(7);
    }

    public void setAdapter(BaseAdapter adapter){
        super.setAdapter(adapter);
        this.adapter = (CalendarAdapter) adapter;
    }

    public BaseAdapter getAdapter(){
        return (BaseAdapter) super.getAdapter();
    }
}