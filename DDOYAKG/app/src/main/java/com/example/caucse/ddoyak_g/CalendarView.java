package com.example.caucse.ddoyak_g;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class CalendarView extends GridView {

    private OnDataSelectionListener selectionListener;
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

        setOnItemClickListener(new OnItemClickAdapter());
    }

    class OnItemClickAdapter implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (adapter != null) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
            }

            if (selectionListener != null) {
                selectionListener.onDataSelected(parent, view, position, id);
            }
        }
    }

    public void setAdapter(BaseAdapter adapter){
        super.setAdapter(adapter);
        this.adapter = (CalendarAdapter) adapter;
    }

    public BaseAdapter getAdapter(){
        return (BaseAdapter) super.getAdapter();
    }

    //item이 선택되었는지 확인해주는 listener 설정
    public void setOnDataSelectionListener(OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }
}